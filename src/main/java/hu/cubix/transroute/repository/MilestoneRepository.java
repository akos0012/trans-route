package hu.cubix.transroute.repository;

import hu.cubix.transroute.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}
