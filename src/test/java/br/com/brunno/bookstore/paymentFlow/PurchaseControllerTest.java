package br.com.brunno.bookstore.paymentFlow;

import br.com.brunno.bookstore.helpers.CustomMockMvc;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.api.constraints.Whitespace;
import net.jqwik.spring.JqwikSpringSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JqwikSpringSupport
@Import(CustomMockMvc.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerTest {

    @Autowired
    CustomMockMvc mockMvc;

    @Property(tries = 50)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void test(
            @ForAll @StringLength(max = 50) @AlphaChars @NumericChars @NotBlank String email,
            @ForAll @StringLength(max = 255) @AlphaChars @NotBlank String name,
            @ForAll @StringLength(max = 255) @AlphaChars @Whitespace @NotBlank String lastName,
            @ForAll @StringLength(max = 255) @AlphaChars @Whitespace @NotBlank String address,
            @ForAll @StringLength(max = 255) @AlphaChars @Whitespace @NotBlank String adjunct,
            @ForAll @StringLength(max = 255) @AlphaChars @Whitespace @NotBlank String city,
            @ForAll @StringLength(value = 13) @NumericChars String phoneNumber,
            @ForAll @StringLength(value = 8) @NumericChars String cep,
            @ForAll @IntRange(min = 1, max = 10) int quantity
    ) throws Exception {
        final BigDecimal bookPrice = BigDecimal.valueOf(49.99);
        mockMvc.post("/author", Map.of(
                "name", "some name",
                "email", "some.email@email.com",
                "description", "some description"
        ));
        mockMvc.post("/country", Map.of("name", "some country"));
        mockMvc.post("/category", Map.of("name", "some category"));
        mockMvc.post("/book",Map.of(
                "title", "some title",
                "digest", "some digest",
                "summary", "some summary",
                "price", bookPrice.toString(),
                "numberOfPages", 200,
                "isbn", "some isbn",
                "publishDate", LocalDate.now().plusDays(1).toString(),
                "categoryId", 1,
                "authorId", 1
        ));

        HashMap<String, Object> payload = new HashMap<>();
        payload.put("email", email + "@email.com");
        payload.put("name", name);
        payload.put("document", "06157125098");
        payload.put("lastName", lastName);
        payload.put("address", address);
        payload.put("adjunct", adjunct);
        payload.put("city", city);
        payload.put("phoneNumber", phoneNumber);
        payload.put("cep", cep);
        payload.put("countryId", 1);
        payload.put("order", Map.of(
                "total", bookPrice.multiply(BigDecimal.valueOf(quantity)),
                "items", List.of(
                        Map.of(
                                "bookId", 1,
                                "quantity", quantity
                        ))
        ));

        mockMvc.post("/purchase", payload)
                .andExpect(status().isCreated());
    }

}
