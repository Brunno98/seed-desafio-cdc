package br.com.brunno.bookstore.state;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.country.CountryRepository;
import br.com.brunno.bookstore.shared.validator.IdExists;
import br.com.brunno.bookstore.shared.validator.UniqueValue;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

@Getter
public class NewStateRequest implements CreateStateDto {

    @NotBlank
    @UniqueValue(domainClass = State.class, fieldName = "name")
    private String name;

    @NotNull
    @Positive
    @IdExists(domain = Country.class)
    private Long countryId;

    @Override
    public State toState(CountryRepository countryRepository) {
        Optional<Country> optionalCountry = countryRepository.findById(countryId);
        Assert.state(optionalCountry.isPresent(), "CountryId "+countryId+" Should exists when create state");
        return new State(name, optionalCountry.get());
    }
}
