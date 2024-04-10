package br.com.brunno.bookstore.country;

import br.com.brunno.bookstore.shared.validator.UniqueValue;
import lombok.Getter;

@Getter
public class NewCountryRequest implements NewCountryDto {

    @UniqueValue(domainClass = Country.class, fieldName = "name")
    private String name;

    @Override
    public Country toCountry() {
        return new Country(name);
    }
}
