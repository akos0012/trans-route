package hu.cubix.transroute.dto;

public record AddressFilterDTO(
        String city,
        String street,
        String countryIsoCode,
        String zipCode
) {
}
