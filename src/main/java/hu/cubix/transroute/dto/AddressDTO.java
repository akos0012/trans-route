package hu.cubix.transroute.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressDTO(
        long id,

        @NotBlank
        @Size(min = 2, max = 2)
        String countryIsoCode,

        @NotBlank
        String city,

        @NotBlank
        String street,

        @NotBlank
        String zipCode,

        @NotBlank
        String houseNumber,

        double latitude,
        double longitude
) {
}
