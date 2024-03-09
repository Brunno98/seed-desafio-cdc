package br.com.brunno.bookstore.state;

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
public class NewStateController {

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    @PostMapping("/state")
    public NewStateResponse createState(@RequestBody @Valid NewStateRequest newStateRequest) {
        State state = newStateRequest.toDomain(entityManager);

        entityManager.persist(state);

        return new NewStateResponse(state);
    }
}
