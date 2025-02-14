package hu.cubix.transroute.service.db;

import hu.cubix.transroute.model.Address;
import hu.cubix.transroute.model.Milestone;
import hu.cubix.transroute.model.Section;
import hu.cubix.transroute.model.TransportPlan;
import hu.cubix.transroute.repository.AddressRepository;
import hu.cubix.transroute.repository.MilestoneRepository;
import hu.cubix.transroute.repository.SectionRepository;
import hu.cubix.transroute.repository.TransportPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InitDbService {

    @Autowired
    private TransportPlanRepository transportPlanRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private AddressRepository addressRepository;

    public void clearDB() {
        sectionRepository.deleteAll();
        milestoneRepository.deleteAll();
        addressRepository.deleteAll();
        transportPlanRepository.deleteAll();
    }

    public void insertTestData() {
        // Creating Addresses
        Address address1 = new Address("HU", "Budapest", "Main St.", "1011", "5A", 47.4979, 19.0402);
        Address address2 = new Address("HU", "Debrecen", "Petofi Sq.", "4025", "10B", 47.5316, 21.6273);
        Address address3 = new Address("HU", "Szeged", "Kossuth Lajos St.", "6720", "8C", 46.2530, 20.1482);

        Address address4 = new Address("HU", "Gyor", "Szechenyi Sq.", "9021", "12D", 47.6875, 17.6504);
        Address address5 = new Address("HU", "Pecs", "Kiraly St.", "7621", "3E", 46.0767, 18.2283);
        Address address6 = new Address("HU", "Budapest", "Andrassy ut", "1061", "20F", 47.5030, 19.0580);
        addressRepository.saveAll(List.of(address1, address2, address3, address4, address5, address6));

        // Creating Milestones
        Milestone milestone1 = new Milestone(address1, LocalDateTime.of(2025, 8, 10, 10, 0));
        Milestone milestone2 = new Milestone(address2, LocalDateTime.of(2025, 8, 11, 15, 30));
        Milestone milestone3 = new Milestone(address2, LocalDateTime.of(2025, 8, 11, 15, 50));
        Milestone milestone4 = new Milestone(address3, LocalDateTime.of(2025, 8, 12, 9, 45));

        Milestone milestone5 = new Milestone(address4, LocalDateTime.of(2025, 9, 2, 8, 0));
        Milestone milestone6 = new Milestone(address5, LocalDateTime.of(2025, 9, 2, 15, 0));
        Milestone milestone7 = new Milestone(address5, LocalDateTime.of(2025, 9, 3, 8, 0));
        Milestone milestone8 = new Milestone(address6, LocalDateTime.of(2025, 9, 3, 16, 15));
        milestoneRepository.saveAll(List.of(milestone1, milestone2, milestone3, milestone4, milestone5, milestone6, milestone7, milestone8));

        // Creating Transport Plan
        double expectedIncome1 = 5000.0;
        TransportPlan transportPlan1 = new TransportPlan(expectedIncome1);
        transportPlanRepository.save(transportPlan1);

        double expectedIncome2 = 8000.0;
        TransportPlan transportPlan2 = new TransportPlan(expectedIncome2);
        transportPlanRepository.save(transportPlan2);

        // Creating Sections
        Section section1 = new Section(transportPlan1, milestone1, milestone2, 0);
        Section section2 = new Section(transportPlan1, milestone3, milestone4, 1);

        Section section3 = new Section(transportPlan2, milestone5, milestone6, 0);
        Section section4 = new Section(transportPlan2, milestone7, milestone8, 1);
        sectionRepository.saveAll(List.of(section1, section2, section3, section4));
    }
}
