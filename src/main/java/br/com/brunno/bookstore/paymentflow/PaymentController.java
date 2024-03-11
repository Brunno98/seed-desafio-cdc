package br.com.brunno.bookstore.paymentflow;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @PostMapping("/buyer-details")
    public String buyerDetails(@RequestBody @Valid BuyerDetailsRequest buyerDetailsRequest) {
        return buyerDetailsRequest.toString();
    }
}
