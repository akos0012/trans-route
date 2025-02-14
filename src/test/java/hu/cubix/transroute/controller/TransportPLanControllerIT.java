package hu.cubix.transroute.controller;

import static org.assertj.core.api.Assertions.assertThat;

import hu.cubix.transroute.dto.DelayRequestDTO;
import hu.cubix.transroute.dto.LoginDTO;
import hu.cubix.transroute.dto.SectionDTO;
import hu.cubix.transroute.dto.TransportPlanDTO;
import hu.cubix.transroute.repository.TransportPlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
//@AutoConfigureWebTestClient(timeout = "10000m")
public class TransportPLanControllerIT {

    private static final String USER = "transport_manager";
    private static final String PASS = "pass";

    private static final String BASIC_URI = "/api/transportPlans";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    TransportPlanRepository transportPlanRepository;

    String jwt;

    @BeforeEach
    void init() {
        LoginDTO loginDTO = new LoginDTO(USER, PASS);

        jwt = webTestClient
                .post()
                .uri("/api/login")
                .bodyValue(loginDTO)
                .exchange()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    void testThatRegisterDelayOnStartMilestoneAndDecreaseIncome() throws Exception {
        long transportPLanId = 1;
        long milestoneId = 1;
        int delayMinutes = 60;

        TransportPlanDTO transportPlanBefore = getTransportPlanById(transportPLanId);
        SectionDTO sectionBefore = findSectionByMilestoneId(transportPlanBefore, milestoneId);

        DelayRequestDTO delayRequestDTO = new DelayRequestDTO(milestoneId, delayMinutes);

        registerDelay(transportPLanId, delayRequestDTO)
                .expectStatus()
                .isOk();

        TransportPlanDTO transportPlanAfter = getTransportPlanById(transportPLanId);
        SectionDTO sectionAfter = findSectionByMilestoneId(transportPlanAfter, milestoneId);

        double percent = 10;
        double expectedIncomeAfter = transportPlanBefore.expectedIncome() * (1 - percent / 100);
        LocalDateTime expectedStartMilestonePlannedTime = sectionBefore.startMilestone().plannedTime().plusMinutes(delayMinutes);
        LocalDateTime expectedEndMilestonePlannedTime = sectionBefore.endMilestone().plannedTime().plusMinutes(delayMinutes);


        assertThat(transportPlanAfter.expectedIncome()).isEqualTo(expectedIncomeAfter);
        assertThat(sectionAfter.startMilestone().plannedTime()).isEqualTo(expectedStartMilestonePlannedTime);
        assertThat(sectionAfter.endMilestone().plannedTime()).isEqualTo(expectedEndMilestonePlannedTime);
    }

    @Test
    void testThatRegisterDelayOnEndMilestoneAndDecreaseIncome() throws Exception {
        long transportPLanId = 1;
        long milestoneId = 2;
        int delayMinutes = 60;

        TransportPlanDTO transportPlanBefore = getTransportPlanById(transportPLanId);
        SectionDTO sectionBefore = findSectionByMilestoneId(transportPlanBefore, milestoneId);
        SectionDTO nextSectionBefore = findNextSection(transportPlanBefore, sectionBefore);

        DelayRequestDTO delayRequestDTO = new DelayRequestDTO(milestoneId, delayMinutes);

        registerDelay(transportPLanId, delayRequestDTO)
                .expectStatus()
                .isOk();

        TransportPlanDTO transportPlanAfter = getTransportPlanById(transportPLanId);
        SectionDTO sectionAfter = findSectionByMilestoneId(transportPlanAfter, milestoneId);
        SectionDTO nextSectionAfter = findNextSection(transportPlanAfter, sectionAfter);

        double percent = 10;
        double expectedIncomeAfter = transportPlanBefore.expectedIncome() * (1 - percent / 100);
        LocalDateTime expectedStartMilestonePlannedTime = sectionBefore.startMilestone().plannedTime();
        LocalDateTime expectedEndMilestonePlannedTime = sectionBefore.endMilestone().plannedTime().plusMinutes(delayMinutes);
        LocalDateTime expectedNextSectionStartMilestonePlannedTime = nextSectionBefore.startMilestone().plannedTime().plusMinutes(delayMinutes);

        assertThat(transportPlanAfter.expectedIncome()).isEqualTo(expectedIncomeAfter);
        assertThat(sectionAfter.startMilestone().plannedTime()).isEqualTo(expectedStartMilestonePlannedTime);
        assertThat(sectionAfter.endMilestone().plannedTime()).isEqualTo(expectedEndMilestonePlannedTime);
        assertThat(nextSectionAfter.startMilestone().plannedTime()).isEqualTo(expectedNextSectionStartMilestonePlannedTime);
    }

    @Test
    void shouldReturnBadRequestWhenMilestoneNotInTransportPlan() throws Exception {
        long transportPLanId = 1;
        long milestoneId = 5;
        int delayMinutes = 60;

        DelayRequestDTO delayRequestDTO = new DelayRequestDTO(milestoneId, delayMinutes);

        registerDelay(transportPLanId, delayRequestDTO)
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void shouldReturnNotFoundForInvalidTransportPlan() throws Exception {
        long transportPLanId = 6;
        long milestoneId = 1;
        int delayMinutes = 60;

        DelayRequestDTO delayRequestDTO = new DelayRequestDTO(milestoneId, delayMinutes);

        registerDelay(transportPLanId, delayRequestDTO)
                .expectStatus()
                .isNotFound();
    }

    private SectionDTO findSectionByMilestoneId(TransportPlanDTO transportPlan, long milestoneId) {
        return transportPlan.sections().stream()
                .filter(s -> s.startMilestone().id() == milestoneId || s.endMilestone().id() == milestoneId)
                .findFirst().get();
    }

    private SectionDTO findNextSection(TransportPlanDTO transportPlan, SectionDTO section) {
        return transportPlan.sections().stream()
                .filter(s -> s.sectionOrder() == section.sectionOrder() + 1)
                .findFirst().get();
    }


    private List<TransportPlanDTO> getAllTransportPlan() {
        List<TransportPlanDTO> responseList = webTestClient
                .get()
                .uri(BASIC_URI)
                .headers(headers -> headers.setBearerAuth(jwt))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TransportPlanDTO.class)
                .returnResult()
                .getResponseBody();

        if (responseList != null)
            responseList.sort(Comparator.comparing(TransportPlanDTO::id));

        return responseList;
    }

    private TransportPlanDTO getTransportPlanById(long id) {
        return webTestClient
                .get()
                .uri(BASIC_URI + "/" + id)
                .headers(headers -> headers.setBearerAuth(jwt))
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransportPlanDTO.class)
                .returnResult()
                .getResponseBody();
    }

    private ResponseSpec registerDelay(long transportPLanID, DelayRequestDTO delayRequestDTO) {
        String path = BASIC_URI + "/" + transportPLanID + "/delay";
        return webTestClient
                .post()
                .uri(path)
                .headers(headers -> headers.setBearerAuth(jwt))
                .bodyValue(delayRequestDTO)
                .exchange();
    }

}
