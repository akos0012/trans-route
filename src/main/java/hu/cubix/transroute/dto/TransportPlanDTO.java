package hu.cubix.transroute.dto;

import java.util.List;

public record TransportPlanDTO(
        long id,
        double expectedIncome,
        List<SectionDTO> sections
) {
}
