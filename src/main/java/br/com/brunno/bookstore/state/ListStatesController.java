package br.com.brunno.bookstore.state;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ListStatesController {

    private final ListStates listStates;

    @GetMapping("/states")
    public List<State> listStates() {
        return listStates.all();
    }
}
