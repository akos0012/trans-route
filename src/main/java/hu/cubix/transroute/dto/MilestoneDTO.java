package hu.cubix.transroute.dto;

import java.time.LocalDateTime;

public record MilestoneDTO(
        long id,
        long addressId,
        LocalDateTime plannedTime
) {
}
