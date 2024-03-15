package br.com.brunno.bookstore.author;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JqwikSpringSupport
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
public class AuthorControllerTest {
    public static final String NAME = "foo";
    public static final String EMAIL = "foo@email.com";
    public static final String DESCRIPTION = "some description";

    private final Set<String> emailsUsed = new HashSet<>();

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TestEntityManager entityManager;

    @Property(tries = 80)
    @Label("Quando criar um author deve-se retornar 200 e o author criado")
    @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
    void test1(@ForAll @StringLength(min = 10, max = 255) @AlphaChars String name,
               @ForAll @StringLength(value = 50) @AlphaChars String email,
               @ForAll @StringLength(min = 10, max = 400) @AlphaChars String description) throws Exception {
        Assumptions.assumeTrue(emailsUsed.add(email));

        String payload = new ObjectMapper()
                .writeValueAsString(Map.of("name", name,
                        "email", email + "@email.com",
                        "description", description));

        mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("id").isNumber(),
                        jsonPath("name").value(name),
                        jsonPath("email").value(email + "@email.com"),
                        jsonPath("description").value(description),
                        jsonPath("registrationInstant").exists()
                );

    }

    @DisplayName("Quando não informar um valor obrigatorio deve-se retornar 400")
    @ParameterizedTest
    @CsvSource(value = {"name","email","description"})
    void test2(String requiredField) throws Exception {
        Map<String, String> payload = new HashMap<>(Map.of(
                "name", NAME,
                "email", EMAIL,
                "description", DESCRIPTION));
        payload.remove(requiredField);
        mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(payload))
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não pode existir 2 autores com o mesmo email")
    @Test
    void test3() throws Exception{
        Author existentAuthor = new Author("existentAuthor", EMAIL, "Existent author");
        entityManager.persist(existentAuthor);

        mockMvc.perform(post("/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(Map.of(
                        "name", NAME,
                        "email", EMAIL,
                        "description", DESCRIPTION)
                )))
                .andExpect(status().isBadRequest());
    }

    @Label("Descrição não pode ser maior que 400 caracteres")
    @Property(tries = 20)
    void test4(@ForAll @AlphaChars @StringLength(min = 401, max = 600) String description) throws Exception{
        mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Map.of(
                                "name", NAME,
                                "email", EMAIL,
                                "description", description)
                        )))
                .andExpect(status().isBadRequest());
    }

}
