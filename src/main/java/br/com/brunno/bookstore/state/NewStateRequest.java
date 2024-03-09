package br.com.brunno.bookstore.state;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.shared.UniqueValue;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class NewStateRequest {

    @NotBlank
    @UniqueValue(domainClass = State.class, fieldName = "name")
    private String name;

    @NotNull
    @Positive
    private Long countryId;

    public State toDomain(EntityManager entityManager) {
        Country country = entityManager.find(Country.class, countryId);

        Assert.state(country != null, "CountryId Should exists when create state");

        return new State(name, country);
    }
}
