package hu.cubix.transroute.mapper;

import hu.cubix.transroute.dto.MilestoneDTO;
import hu.cubix.transroute.model.Milestone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MilestoneMapper {

    @Mapping(source = "address.id", target = "addressId")
    MilestoneDTO milestoneToDto(Milestone milestone);

    Milestone dtoToMilestone(MilestoneDTO milestoneDTO);

    List<MilestoneDTO> milestonesToDtos(List<Milestone> milestones);
}
