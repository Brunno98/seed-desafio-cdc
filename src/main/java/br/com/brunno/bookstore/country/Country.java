package br.com.brunno.bookstore.country;

import br.com.brunno.bookstore.state.State;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "country")
    private List<State> states = new ArrayList<>();

    @Deprecated
    public Country() {}

    public Country(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assert.notNull(name, "Country should have a name when equals verify");
        Country country = (Country) o;
        return name.equalsIgnoreCase(country.name);
    }

    public boolean hasState() {
        return !states.isEmpty();
    }
}
