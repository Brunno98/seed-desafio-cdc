package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.paymentflow.validator.StateBelongToCountryValidator;
import br.com.brunno.bookstore.paymentflow.validator.TotalMatchCartItemsValidator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
public class PurchaseController {

    private final StateBelongToCountryValidator stateBelongToCountryValidator;
    private final TotalMatchCartItemsValidator totalMatchCartItemsValidator;
    private final EntityManager entityManager;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(stateBelongToCountryValidator);
        webDataBinder.addValidators(totalMatchCartItemsValidator);
    }

    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/purchase")
    public String createPurchase(@RequestBody @Valid NewPurchaseRequest newPurchaseRequest) {
        Purchase purchase = newPurchaseRequest.toDomain(entityManager);
        entityManager.persist(purchase);
        return "/purchase/" + purchase.getId();
    }

    @GetMapping("/purchase/{id}")
    public PurchaseDetailsResponse getPurchaseDetails(@PathVariable String id) {
        Purchase purchase = entityManager.find(Purchase.class, id);

        if (purchase == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new PurchaseDetailsResponse(purchase);
    }
}
