package br.com.brunno.bookstore.country;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListCountries {

    private final CountryRepository countryRepository;

    public List<Country> all() {
        return countryRepository.findAll();
    }

}
