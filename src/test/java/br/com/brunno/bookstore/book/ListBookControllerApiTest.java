package br.com.brunno.bookstore.book;

import br.com.brunno.bookstore.helpers.CustomMockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(CustomMockMvc.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ListBookControllerApiTest {

    @Autowired
    CustomMockMvc mockMvc;

    @Test
    @DisplayName("Deve retornar os detalhes do livro criado")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void test() throws Exception {
        // create author
        String authorName = "some name";
        String authorEmail = "some.email@email.com";
        String authorDescription = "some description";
        mockMvc.post("/author", Map.of(
                        "name", authorName,
                        "email", authorEmail,
                        "description", authorDescription
                ));

        // create category
        String categoryName = "some category";
        mockMvc.post("/category", Map.of("name", categoryName));

        // create book
        String title = "some title";
        String digest = "some digest";
        String summary = "some summary";
        double price = 39.99;
        int numberOfPages = 200;
        String isbn = "some isbn";
        String publishDate = LocalDate.now().plusDays(1).toString();
        mockMvc.post("/book", Map.of(
                        "title", title,
                        "digest", digest,
                        "summary", summary,
                        "price", price,
                        "numberOfPages", numberOfPages,
                        "isbn", isbn,
                        "publishDate", publishDate,
                        "categoryId", 1,
                        "authorId", 1
                ));

        mockMvc.get("/book/1")
            .andExpectAll(
                            status().isOk(),
                            jsonPath("id").value(1),
                            jsonPath("title").value(title),
                            jsonPath("digest").value(digest),
                            jsonPath("summary").value(summary),
                            jsonPath("price").value(price),
                            jsonPath("numberOfPages").value(numberOfPages),
                            jsonPath("isbn").value(isbn),
                            jsonPath("publishDate").value(publishDate),
                            jsonPath("category.id").value(1),
                            jsonPath("category.name").value(categoryName),
                            jsonPath("author.id").value(1),
                            jsonPath("author.name").value(authorName),
                            jsonPath("author.email").value(authorEmail),
                            jsonPath("author.description").value(authorDescription),
                            jsonPath("author.registrationInstant").exists()
                    );
    }
}
