package br.com.brunno.bookstore.author;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final EntityManager entityManager;
    private final Clock clock;

    @Autowired
    public AuthorController(EntityManager entityManager, Clock clock) {
        this.entityManager = entityManager;
        this.clock = clock;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CreateAuthorResponse> createAuthor(@RequestBody @Valid CreateAuthorRequest createRequest) {
        Author author = createRequest.toDomain(clock);

        entityManager.persist(author);

        CreateAuthorResponse response = CreateAuthorResponse.from(author) ;

        return ResponseEntity.ok(response);
    }
}
