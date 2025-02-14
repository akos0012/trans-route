package hu.cubix.transroute.dto;

import jakarta.validation.constraints.Positive;

public record DelayRequestDTO(
        long milestoneID,

        @Positive
        int minutes
) {
}
