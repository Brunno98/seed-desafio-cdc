package br.com.brunno.bookstore.paymentFlow;

import br.com.brunno.bookstore.helpers.CustomMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomMockMvc.class)
public class PurchaseDetailsTest {

    @Autowired
    CustomMockMvc mockMvc;

    @Test
    void test() throws Exception {
        final BigDecimal bookPrice = BigDecimal.valueOf(49.99);
        mockMvc.post("/author", Map.of(
                "name", "some name",
                "email", "some.email@email.com",
                "description", "some description"
        ));
        mockMvc.post("/country", Map.of("name", "some country"));
        mockMvc.post("/category", Map.of("name", "some category"));
        mockMvc.post("/book", Map.of(
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
        payload.put("email", "some.email@email.com");
        payload.put("name", "some name");
        payload.put("document", "06157125098");
        payload.put("lastName", "some last name");
        payload.put("address", "some address");
        payload.put("adjunct", "some adjunct");
        payload.put("city", "some city");
        payload.put("phoneNumber", "5521987654321");
        payload.put("cep", "12345678");
        payload.put("countryId", 1);
        payload.put("order", Map.of(
                "total", bookPrice.toString(),
                "items", List.of(
                        Map.of(
                                "bookId", 1,
                                "quantity", 1
                        ))
        ));

        ResultActions result = mockMvc.post("/purchase", payload);

        String purchaseLocation = result.andReturn().getResponse().getContentAsString();
        mockMvc.get(purchaseLocation).andExpect(status().isOk());
    }
}
