package br.com.brunno.bookstore.coupon;

import br.com.brunno.bookstore.helpers.CustomMockMvc;
import jakarta.validation.constraints.Digits;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.DoubleRange;
import net.jqwik.api.constraints.NotBlank;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@JqwikSpringSupport
@Import(CustomMockMvc.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NewCouponControllerTest {

    private static final Set<String> existentCodes = new HashSet<>();

    @Autowired
    CustomMockMvc mockMvc;

    @Property(tries = 50)
    @Label("")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void test(
            @ForAll @StringLength(max = 255) @AlphaChars @NumericChars @NotBlank String code,
            @ForAll @DoubleRange(min = 0, max = 1, minIncluded = false) @Digits(integer = 1, fraction = 2) double discount
    ) throws Exception {
        Assumptions.assumeTrue(existentCodes.add(code));

        Map<String, Object> payload = Map.of(
                "code", code,
                "discount", discount,
                "expirationDate", LocalDate.now().plusDays(1));

        mockMvc.post("/coupon", payload)
                .andExpectAll(
                        status().isOk(),
                        jsonPath("code").value(code)
                );

        mockMvc.post("/coupon", payload)
                .andExpect(status().isBadRequest());
    }
}
