package br.com.brunno.bookstore.state;

import br.com.brunno.bookstore.helpers.CustomMockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomMockMvc.class)
public class NewStateControllerTest {

    @Autowired
    CustomMockMvc mockMvc;



    @Test
    @DisplayName("Deve criar estado e retornar 200")
    void test() throws Exception {
        mockMvc.post("/country", Map.of("name", "some country"));

        Map<String, Object> payload = Map.of("name", "some name","countryId", 1);

        mockMvc.post("/state", payload)
                .andExpect(status().isOk());

        mockMvc.post("/state", payload)
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Tentar criar estado sem pais deve retornar bad request")
    void notCountry() throws Exception {
        Map<String, Object> payload = Map.of("name", "some name");
        mockMvc.post("/state", payload)
                .andExpect(status().isBadRequest());
    }
}
