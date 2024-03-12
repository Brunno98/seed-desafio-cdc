package br.com.brunno.bookstore.country;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ListCountryController {

    private final EntityManager entityManager;

    @GetMapping("/country")
    public List<Country> listCountries() {
        List resultList = entityManager.createQuery("FROM Country").getResultList();
        return resultList;
    }
}
