package br.com.brunno.bookstore.country;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CountryTest {

    @Test
    void countriesWithDifferentName() {
        Country country = new Country("Brazil");
        Country otherCountry = new Country("Argentina");

        Assertions.assertThat(country.equals(otherCountry)).isFalse();
    }

    @Test
    void countriesWithSameName() {
        Country country = new Country("Brazil");
        Country otherCountry = new Country("Brazil");

        Assertions.assertThat(country.equals(otherCountry)).isTrue();
    }

    @Test
    void countriesWithSameNameButDiferentCase() {
        Country lowerCaseCountry = new Country("brazil");
        Country upperCaseCountry = new Country("BRAZIL");

        Assertions.assertThat(lowerCaseCountry.equals(upperCaseCountry)).isTrue();
    }

    @Test
    void sameObject() {
        Country country = new Country("Brazil");

        Assertions.assertThat(country.equals(country)).isTrue();
    }

    @Test
    void nullObject() {
        Country country = new Country("Brazil");

        Assertions.assertThat(country.equals(null)).isFalse();
    }

    @Test
    void genericObject() {
        Country country = new Country("Brazil");

        Assertions.assertThat(country.equals(new Object())).isFalse();
    }

    @Test
    void differentObject() {
        Country country = new Country("Brazil");

        Assertions.assertThat(country.equals(new String())).isFalse();
    }
}
