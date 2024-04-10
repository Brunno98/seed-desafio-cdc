package br.com.brunno.bookstore.state;

import br.com.brunno.bookstore.country.CountryRepository;

public interface CreateStateDto {

    State toState(CountryRepository countryRepository);

}
