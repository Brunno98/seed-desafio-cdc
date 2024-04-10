package br.com.brunno.bookstore.country;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ListCountryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Should return list of countries")
    void listCountries() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/country")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "foo"}
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/country")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"name": "bar"}
                        """))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/country"))
                .andExpectAll(MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("$").isArray(),
                        MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }
}