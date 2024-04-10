package br.com.brunno.bookstore.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.api.constraints.Whitespace;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private Set<String> usedNames = new HashSet<>();

    @Autowired
    MockMvc mockMvc;

    @Label("Should create category")
    @Property(tries = 20)
    void createCategory(
            @ForAll @StringLength(max = 255) @NotBlank @AlphaChars @NumericChars @Whitespace String name
    ) throws Exception {
        Assumptions.assumeTrue(usedNames.add(name));

        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of(
                        "name", name
                ))))
                .andExpect(status().isOk());

        // Returns BAD REQUEST when category name already exists
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "name", name
                        ))))
                .andExpect(status().isBadRequest());
    }
}