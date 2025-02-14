package hu.cubix.transroute.service;

import hu.cubix.transroute.dto.AddressFilterDTO;
import hu.cubix.transroute.model.Address;
import hu.cubix.transroute.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Optional<Address> findById(long id) {
        return addressRepository.findById(id);
    }

    @Transactional
    public void delete(long id) {
        addressRepository.deleteById(id);
    }

    @Transactional
    public Address update(Address address) {
        if (!addressRepository.existsById(address.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return addressRepository.save(address);
    }

    public Page<Address> findAddressByExample(AddressFilterDTO example, Pageable pageable) {
        String city = example.city();
        String street = example.street();
        String countryIsoCode = example.countryIsoCode();
        String zipCode = example.zipCode();

        Specification<Address> spec = Specification.where(null);

        if (StringUtils.hasText(city))
            spec = spec.and(AddressSpecification.hasCity(city));
        if (StringUtils.hasText(street))
            spec = spec.and(AddressSpecification.hasStreet(street));
        if (StringUtils.hasText(countryIsoCode))
            spec = spec.and(AddressSpecification.hasCountryIsoCode(countryIsoCode));
        if (StringUtils.hasText(zipCode))
            spec = spec.and(AddressSpecification.hasZipCode(zipCode));

        return addressRepository.findAll(spec, pageable);
    }


}
