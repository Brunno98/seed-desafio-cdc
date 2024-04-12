package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.paymentflow.webvalidator.CouponValidator;
import br.com.brunno.bookstore.paymentflow.webvalidator.StateBelongToCountryValidator;
import br.com.brunno.bookstore.paymentflow.webvalidator.TotalMatchCartItemsValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PurchaseController {

    private final StateBelongToCountryValidator stateBelongToCountryValidator;
    private final TotalMatchCartItemsValidator totalMatchCartItemsValidator;
    private final CouponValidator couponValidator;
    private final InitiatePurchaseService initiatePurchaseService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(stateBelongToCountryValidator);
        webDataBinder.addValidators(totalMatchCartItemsValidator);
        webDataBinder.addValidators(couponValidator);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/purchase")
    public String createPurchase(@RequestBody @Valid NewPurchaseRequest newPurchaseRequest) {
        Purchase purchase = initiatePurchaseService.execute(newPurchaseRequest);
        return "/purchase/" + purchase.getId();
    }
}
