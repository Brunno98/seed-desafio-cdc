package br.com.brunno.bookstore.author;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
public class AuthorControllerTest {
    public static final String NAME = "foo";
    public static final String EMAIL = "foo@email.com";
    public static final String DESCRIPTION = "some description";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    Clock clock;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    public void init() {
        Mockito.when(clock.getZone()).thenReturn(ZoneId.of("America/Sao_Paulo"));
        Mockito.when(clock.instant()).thenReturn(Instant.parse("2024-03-14T14:50:12.123Z"));
    }

    @DisplayName("Quando criar um author deve-se retornar 200 e o author criado")
    @Test
    void test1() throws Exception {
        mockMvc.perform(post("/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(Map.of(
                        "name", NAME,
                        "email", EMAIL,
                        "description", DESCRIPTION)
                )))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("id").value(1),
                        jsonPath("name").value("foo"),
                        jsonPath("email").value("foo@email.com"),
                        jsonPath("description").value("some description"),
                        jsonPath("registrationInstant").value("2024-03-14T11:50:12.123")
                );

    }

    @DisplayName("Quando não informar um valor obrigatorio deve-se retornar 400")
    @ParameterizedTest
    @CsvSource(value = {"name","email","description"})
    void test2(String requiredField) throws Exception {
        Map<String, String> body = new HashMap<>(Map.of(
                "name", NAME,
                "email", EMAIL,
                "description", DESCRIPTION));
        body.remove(requiredField);
        mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                new ObjectMapper().writeValueAsString(body)
                        )
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Não pode existir 2 autores com o mesmo email")
    @Test
    void test3() throws Exception{
        Author existentAuthor = new Author("existentAuthor", EMAIL, "Existent author", clock);
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

    @DisplayName("Descrição não pode ser maior que 400 caracteres")
    @Test
    void test4() throws Exception{
        String bigDescription = "Inthecenterofthebusycity,farfromtherushandnoise,thereliesahiddengardenwherepeaceandserenityabound.Beneaththeshadysycamores,whispersoftleavesandbirdsongsserenadevisitors,offeringasafehavenfromthetumultofurbanlife.Withineachpetal,asecretgardenoftranquilityawaits,atimelessoasisamidstthefrenzyofmodernity.Inthecenterofthebusycity,farfromtherushandnoise,thereliesahiddengardenwherepeaceandserenityabound.B";
        mockMvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(Map.of(
                                "name", NAME,
                                "email", EMAIL,
                                "description", bigDescription)
                        )))
                .andExpect(status().isBadRequest());
    }

}
