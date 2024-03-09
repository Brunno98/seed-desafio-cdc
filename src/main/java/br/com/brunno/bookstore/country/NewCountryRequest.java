package br.com.brunno.bookstore.country;

import br.com.brunno.bookstore.shared.validator.UniqueValue;
import lombok.Getter;

@Getter
public class NewCountryRequest {

    @UniqueValue(domainClass = Country.class, fieldName = "name")
    private String name;

    public Country toDomain() {
        return new Country(name);
    }
}
