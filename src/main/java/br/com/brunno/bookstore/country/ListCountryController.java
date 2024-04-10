package br.com.brunno.bookstore.country;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ListCountryController {

    private final ListCountries listCountries;

    @GetMapping("/country")
    public List<Country> listCountries() {
        return listCountries.all();
    }
}
