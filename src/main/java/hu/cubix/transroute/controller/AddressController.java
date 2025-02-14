package hu.cubix.transroute.controller;

import hu.cubix.transroute.dto.AddressDTO;
import hu.cubix.transroute.dto.AddressFilterDTO;
import hu.cubix.transroute.mapper.AddressMapper;
import hu.cubix.transroute.model.Address;
import hu.cubix.transroute.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressMapper addressMapper;

    @GetMapping
    public List<AddressDTO> findAll() {
        return addressMapper.addressesToDtos(addressService.findAll());
    }

    @GetMapping("/{id}")
    public AddressDTO findById(@PathVariable long id) {
        Address address = addressService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return addressMapper.addressToDto(address);
    }

    @PostMapping
    public long createAddress(@RequestBody @Valid AddressDTO addressDTO) {
        if (addressDTO.id() != 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Address address = addressMapper.dtoToAddress(addressDTO);
        Address newAddress = addressService.save(address);
        return addressMapper.addressToDto(newAddress).id();
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable long id) {
        addressService.delete(id);
    }

    @PutMapping("/{id}")
    public AddressDTO updateAddress(@PathVariable long id, @RequestBody @Valid AddressDTO addressDTO) {
        if (addressDTO.id() != 0 && id != addressDTO.id())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Address address = addressMapper.dtoToAddress(addressDTO);
        address.setId(id);

        Address newAddress = addressService.update(address);
        return addressMapper.addressToDto(newAddress);
    }

    @PostMapping("/search")
    public ResponseEntity<List<AddressDTO>> searchAddress(@RequestBody AddressFilterDTO addressFilterDTO, @SortDefault("id") Pageable pageable) {
        if (addressFilterDTO == null || isEmpty(addressFilterDTO))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Page<Address> page = addressService.findAddressByExample(addressFilterDTO, pageable);
        List<AddressDTO> addressDTOS = addressMapper.addressesToDtos(page.getContent());

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(page.getTotalElements()))
                .body(addressDTOS);
    }

    private boolean isEmpty(AddressFilterDTO filter) {
        return (filter.city() == null || filter.city().isBlank()) &&
                (filter.street() == null || filter.street().isBlank()) &&
                (filter.countryIsoCode() == null || filter.countryIsoCode().isBlank()) &&
                (filter.zipCode() == null || filter.zipCode().isBlank());
    }
}
