package br.com.brunno.bookstore.author;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public AuthorController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<CreateAuthorResponse> createAuthor(@RequestBody @Valid CreateAuthorRequest createRequest) {
        Author author = createRequest.toDomain();

        List resultList = entityManager.createQuery("FROM Author a WHERE UPPER(a.email) = UPPER(:email)")
                .setParameter("email", author.getEmail())
                .getResultList();

        if (!resultList.isEmpty()) {
            throw new IllegalArgumentException("Email " + author.getEmail() + " already exits");
        }

        entityManager.persist(author);

        CreateAuthorResponse response = CreateAuthorResponse.from(author) ;

        return ResponseEntity.ok(response);
    }
}
