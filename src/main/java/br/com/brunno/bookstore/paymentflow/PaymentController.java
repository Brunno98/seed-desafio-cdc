package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.paymentflow.validator.StateBelongToCountryValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final StateBelongToCountryValidator stateBelongToCountryValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(stateBelongToCountryValidator);
    }

    @PostMapping("/buyer-details")
    public String buyerDetails(@RequestBody @Valid BuyerDetailsRequest buyerDetailsRequest) {
        return buyerDetailsRequest.toString();
    }
}
