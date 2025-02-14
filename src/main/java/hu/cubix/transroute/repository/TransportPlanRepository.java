package hu.cubix.transroute.repository;

import hu.cubix.transroute.model.TransportPlan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransportPlanRepository extends JpaRepository<TransportPlan, Long> {

    @EntityGraph(attributePaths = {"sections", "sections.startMilestone", "sections.endMilestone"})
    @Query("SELECT tp FROM TransportPlan tp")
    List<TransportPlan> findAllWithSections();

    @EntityGraph(attributePaths = {"sections", "sections.startMilestone", "sections.endMilestone"})
    @Query("SELECT tp FROM TransportPlan tp WHERE tp.id = :id")
    Optional<TransportPlan> findByIdWithSections(long id);
}
