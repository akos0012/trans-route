package hu.cubix.transroute.service;

import hu.cubix.transroute.model.Address;
import hu.cubix.transroute.model.Address_;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {

    public static Specification<Address> hasCity(String city) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.city)), (city + "%").toLowerCase());
    }

    public static Specification<Address> hasStreet(String street) {
        return (root, cq, cb) -> cb.like(cb.lower(root.get(Address_.street)), (street + "%").toLowerCase());
    }

    public static Specification<Address> hasCountryIsoCode(String countryIsoCode) {
        return (root, cq, cb) -> cb.equal(root.get(Address_.countryIsoCode), countryIsoCode);
    }

    public static Specification<Address> hasZipCode(String zipCode) {
        return (root, cq, cb) -> cb.equal(root.get(Address_.zipCode), zipCode);
    }
}
