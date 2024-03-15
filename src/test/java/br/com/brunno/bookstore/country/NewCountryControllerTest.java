package br.com.brunno.bookstore.country;

import br.com.brunno.bookstore.helpers.CustomMockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(CustomMockMvc.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NewCountryControllerTest {

    @Autowired
    CustomMockMvc mockMvc;

    @Test
    @DisplayName("Criar um pais deve retornar 200 e body")
    void test() throws Exception {
        mockMvc.post("/country", Map.of("name", "Brazil"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("name").value("Brazil")
                );

        mockMvc.post("/country", Map.of("name", "Brazil"))
                .andExpect(status().isBadRequest());
    }
}
