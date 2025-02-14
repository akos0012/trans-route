package hu.cubix.transroute.service;

import hu.cubix.transroute.model.Milestone;
import hu.cubix.transroute.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    public Milestone save(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    public List<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    public Optional<Milestone> findById(long id) {
        return milestoneRepository.findById(id);
    }

}
