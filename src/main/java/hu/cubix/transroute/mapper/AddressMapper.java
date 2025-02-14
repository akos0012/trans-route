package hu.cubix.transroute.mapper;

import hu.cubix.transroute.dto.AddressDTO;
import hu.cubix.transroute.model.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDTO addressToDto(Address address);

    Address dtoToAddress(AddressDTO addressDTO);

    List<AddressDTO> addressesToDtos(List<Address> addresses);
}
