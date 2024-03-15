package br.com.brunno.bookstore.book;

import br.com.brunno.bookstore.author.Author;
import br.com.brunno.bookstore.category.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.DoubleRange;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.api.constraints.Scale;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.api.constraints.WithNull;
import net.jqwik.spring.JqwikSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@JqwikSpringSupport
@AutoConfigureTestEntityManager
@AutoConfigureMockMvc
@SpringBootTest
public class CreateBookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestEntityManager entityManager;

    @Transactional
    @Property(tries = 50)
    @Label("Quando criar um livro deve-se retornar 200 e o livro no body")
    @DirtiesContext(classMode =  DirtiesContext.ClassMode.BEFORE_CLASS)
    void createBook(
            @ForAll @AlphaChars @NotBlank String title,
            @ForAll @StringLength(max = 500) @AlphaChars @NotBlank String digest,
            @ForAll @StringLength(max = 1000) @AlphaChars @WithNull String summary,
            @ForAll @DoubleRange(min = 20, max = 9999999999L) @Scale(2) double price,
            @ForAll @IntRange(min = 100) int numberOfPages,
            @ForAll @AlphaChars @NotBlank String isbn
    ) throws Exception {
        Category category = new Category("some category");
        entityManager.persist(category);
        Author author = new Author("some name", "some.email@email.com", "some description");
        entityManager.persist(author);

        HashMap<String, Object> content = new HashMap<>(Map.of(
                "title", title,
                "digest", digest,
                "price", price,
                "numberOfPages", numberOfPages,
                "isbn", isbn,
                "publishDate", LocalDate.now().plusDays(1).toString(),
                "categoryId", category.getId(),
                "authorId", author.getId()
        ));
        if (Objects.nonNull(summary)) content.put("summary", summary);

        String payload = new ObjectMapper().writeValueAsString(content);

        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
