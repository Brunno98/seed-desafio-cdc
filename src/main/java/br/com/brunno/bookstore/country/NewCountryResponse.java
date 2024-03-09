package br.com.brunno.bookstore.country;

import lombok.Getter;

@Getter
public class NewCountryResponse {

    private final Long id;
    private final String name;

    public NewCountryResponse(Country country) {
        id = country.getId();
        name = country.getName();
    }
}
