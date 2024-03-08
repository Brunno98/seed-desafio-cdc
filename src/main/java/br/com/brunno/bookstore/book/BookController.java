package br.com.brunno.bookstore.book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

    @PersistenceContext
    private final EntityManager entityManager;

    @Transactional
    @PostMapping
    public NewBookResponse createBook(@RequestBody @Valid NewBookRequest newBookRequest) {
        Book book = newBookRequest.toDomain(entityManager);

        entityManager.persist(book);

        return NewBookResponse.from(book);
    }
}
