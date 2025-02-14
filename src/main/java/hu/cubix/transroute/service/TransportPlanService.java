package hu.cubix.transroute.service;

import hu.cubix.transroute.dto.DelayRequestDTO;
import hu.cubix.transroute.model.Milestone;
import hu.cubix.transroute.model.Section;
import hu.cubix.transroute.model.TransportPlan;
import hu.cubix.transroute.repository.MilestoneRepository;
import hu.cubix.transroute.repository.TransportPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TransportPlanService {

    @Autowired
    private TransportPlanRepository transportPlanRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private DelayService delayService;

    public TransportPlan save(TransportPlan transportPlan) {
        return transportPlanRepository.save(transportPlan);
    }

    public List<TransportPlan> findAll() {
        return transportPlanRepository.findAllWithSections();
    }

    public Optional<TransportPlan> findById(long id) {
        return transportPlanRepository.findByIdWithSections(id);
    }

    @Transactional
    public void registerDelay(long transportPLanID, DelayRequestDTO delayRequest) {
        long milestoneID = delayRequest.milestoneID();
        int minutes = delayRequest.minutes();

        TransportPlan transportPlan = transportPlanRepository.findById(transportPLanID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Milestone milestone = milestoneRepository.findById(milestoneID)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Section section = findSection(transportPlan, milestoneID);

        updateMilestonePlannedTime(milestone, minutes);

        double decreasePercent = delayService.getIncomeDecreasePercent(minutes);
        transportPlan.setExpectedIncome(transportPlan.getExpectedIncome() * (1 - decreasePercent / 100));

        propagateDelay(section, milestone, minutes);

        milestoneRepository.save(milestone);
        transportPlanRepository.save(transportPlan);
    }

    private void updateMilestonePlannedTime(Milestone milestone, int minutes) {
        milestone.setPlannedTime(milestone.getPlannedTime().plusMinutes(minutes));
    }

    private Section findSection(TransportPlan transportPlan, long milestoneID) {
        return transportPlan.getSections().stream()
                .filter(s -> s.getStartMilestone().getId() == milestoneID
                        || s.getEndMilestone().getId() == milestoneID)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    private void propagateDelay(Section section, Milestone milestone, int minutes) {
        if (section.getStartMilestone().equals(milestone)) {
            updateMilestonePlannedTime(section.getEndMilestone(), minutes);
            milestoneRepository.save(section.getEndMilestone());
        } else if (section.getEndMilestone().equals(milestone)) {
            section.getTransportPlan().getSections().stream()
                    .filter(s -> s.getSectionOrder() == section.getSectionOrder() + 1)
                    .findFirst()
                    .ifPresent(nextSection -> {
                        updateMilestonePlannedTime(nextSection.getStartMilestone(), minutes);
                        milestoneRepository.save(nextSection.getStartMilestone());
                    });
        }
    }


}
