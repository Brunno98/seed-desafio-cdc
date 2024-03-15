package br.com.brunno.bookstore.state;

import br.com.brunno.bookstore.country.Country;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.util.Assert;

import java.util.Objects;

@Entity
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @Deprecated
    public State() {}

    public State(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountryName() {
        return country.getName();
    }

    public boolean belongTo(Country country) {
        Assert.state(Objects.nonNull(this.country), "Cannot verify if state belong to country when country field is null");
        return this.country.equals(country);
    }
}
