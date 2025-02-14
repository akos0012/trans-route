package hu.cubix.transroute.mapper;

import hu.cubix.transroute.dto.TransportPlanDTO;
import hu.cubix.transroute.model.TransportPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SectionMapper.class})
public interface TransportPlanMapper {

    @Mapping(target = "sections", source = "sections")
    TransportPlanDTO transportPlanToDto(TransportPlan transportPlan);

    TransportPlan dtoToTransportPlan(TransportPlanDTO transportPlanDTO);

    List<TransportPlanDTO> transportPlansToDtos(List<TransportPlan> transportPlans);

}
