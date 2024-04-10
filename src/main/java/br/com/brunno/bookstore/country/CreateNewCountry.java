package br.com.brunno.bookstore.country;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNewCountry {

    private final CountryRepository countryRepository;

    public Country create(NewCountryDto newCountryDto) {
        Country country = newCountryDto.toCountry();
        return countryRepository.save(country);
    }
}
