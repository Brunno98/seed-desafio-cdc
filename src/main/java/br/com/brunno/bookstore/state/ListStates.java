package br.com.brunno.bookstore.state;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListStates {

    private final StateRepository stateRepository;

    public List<State> all() {
        return stateRepository.findAll();
    }
}
