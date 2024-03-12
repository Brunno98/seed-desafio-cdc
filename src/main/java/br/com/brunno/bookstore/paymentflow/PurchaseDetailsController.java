package br.com.brunno.bookstore.paymentflow;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
public class PurchaseDetailsController {

    private final EntityManager entityManager;

    @GetMapping("/purchase/{id}")
    public PurchaseDetailsResponse getPurchaseDetails(@PathVariable String id) {
        Purchase purchase = entityManager.find(Purchase.class, id);

        if (purchase == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return new PurchaseDetailsResponse(purchase);
    }
}
