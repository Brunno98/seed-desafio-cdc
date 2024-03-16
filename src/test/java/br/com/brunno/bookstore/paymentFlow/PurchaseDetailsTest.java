package br.com.brunno.bookstore.paymentFlow;

import br.com.brunno.bookstore.helpers.CustomMockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(CustomMockMvc.class)
public class PurchaseDetailsTest {

    @Autowired
    CustomMockMvc mockMvc;

    private final BigDecimal bookPrice = BigDecimal.valueOf(49.99);
    private final BigDecimal couponDiscount = BigDecimal.valueOf(0.1);
    private final Map<String, Object> authorPaload = Map.of(
            "name", "some name",
            "email", "some.email@email.com",
            "description", "some description"
    );
    private final Map<String, Object> countryPayload = Map.of("name", "some country");
    private final Map<String, Object> categoryPayload = Map.of("name", "some category");
    private final Map<String, Object> bookPayload = Map.of(
            "title", "some title",
            "digest", "some digest",
            "summary", "some summary",
            "price", bookPrice.toString(),
            "numberOfPages", 200,
            "isbn", "some isbn",
            "publishDate", LocalDate.now().plusDays(1).toString(),
            "categoryId", 1,
            "authorId", 1
    );
    private final Map<String, Object> couponPayload = Map.of("code", "test123",
            "discount", couponDiscount.toString(),
            "expirationDate", LocalDate.now().plusDays(1).toString());

    @DisplayName("Retorna detalhes da compra")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void test() throws Exception {
        mockMvc.post("/author", authorPaload);
        mockMvc.post("/country", countryPayload);
        mockMvc.post("/category", categoryPayload);
        mockMvc.post("/book", bookPayload);

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

        String purchaseLocation = result
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        mockMvc.get(purchaseLocation).andExpect(status().isOk());
    }

    @DisplayName("Compras com cupom aplicado devem retornar detalhes do cupom e o valor final com desconto")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void coupon() throws Exception {
        mockMvc.post("/author", authorPaload);
        mockMvc.post("/country", countryPayload);
        mockMvc.post("/category", categoryPayload);
        mockMvc.post("/book", bookPayload);
        mockMvc.post("/coupon", couponPayload);

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
        payload.put("coupon", "test123");

        ResultActions result = mockMvc.post("/purchase", payload);

        String purchaseLocation = result
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        BigDecimal expectedFinalPrice = bookPrice.subtract(bookPrice.multiply(couponDiscount));
        mockMvc.get(purchaseLocation)
                .andExpect(status().isOk())
                .andExpect(jsonPath("finalValue").value(expectedFinalPrice));
    }

    @Test
    @DisplayName("nao encontrar a comprar deve retornar not found")
    void notFound() throws Exception {
        mockMvc.get("/purchase/not-existent")
                .andExpect(status().isNotFound());
    }
}
