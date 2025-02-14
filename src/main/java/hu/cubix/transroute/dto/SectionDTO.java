package hu.cubix.transroute.dto;

public record SectionDTO(
        long id,
        int sectionOrder,
        long transportPlanId,
        MilestoneDTO startMilestone,
        MilestoneDTO endMilestone
) {
}
