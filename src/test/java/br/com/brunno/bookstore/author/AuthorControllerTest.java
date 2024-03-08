package br.com.brunno.bookstore.author;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@AutoConfigureTestEntityManager
@WebMvcTest
public class AuthorControllerTest {

    @MockBean
    EntityManagerFactory entityManagerFactory;

    @MockBean
    EntityManager entityManager;

    @MockBean
    AuthorRepository authorRepository;

    @Test
    void test1() {

    }

}
