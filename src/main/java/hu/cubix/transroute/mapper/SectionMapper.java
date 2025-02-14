package hu.cubix.transroute.mapper;

import hu.cubix.transroute.dto.SectionDTO;
import hu.cubix.transroute.model.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    @Mapping(source = "transportPlan.id", target = "transportPlanId")
    @Mapping(source = "startMilestone.address.id", target = "startMilestone.addressId")
    @Mapping(source = "endMilestone.address.id", target = "endMilestone.addressId")
    SectionDTO sectionToDto(Section section);

    Section dtoToSection(SectionDTO sectionDTO);

    List<SectionDTO> sectionsToDtos(List<Section> sections);

    List<Section> dtosToSections(List<SectionDTO> sectionDTOS);
}
