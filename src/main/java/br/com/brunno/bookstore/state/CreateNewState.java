package br.com.brunno.bookstore.state;

import br.com.brunno.bookstore.country.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateNewState {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    public State create(CreateStateDto createStateDto) {
        State state = createStateDto.toState(countryRepository);
        return stateRepository.save(state);
    }
}
