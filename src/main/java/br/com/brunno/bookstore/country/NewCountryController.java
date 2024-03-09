package br.com.brunno.bookstore.country;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NewCountryController {

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    @PostMapping("/country")
    public NewCountryResponse createCountry(@RequestBody @Valid NewCountryRequest newCountryRequest) {
        Country country = newCountryRequest.toDomain();

        entityManager.persist(country);

        return new NewCountryResponse(country);
    }
}
