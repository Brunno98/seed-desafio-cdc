package br.com.brunno.bookstore.paymentflow;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PurchaseDetailsController {

    private final EntityManager entityManager;

    @GetMapping("/purchase/{id}")
    public PurchaseDetailsResponse getPurchaseDetails(@PathVariable String id) {
        Purchase purchase = entityManager.find(Purchase.class, id);

        Optional.ofNullable(purchase).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        PurchaseDetailsResponse response = new PurchaseDetailsResponse(purchase);
        if (response.isAppliedCupon()) {
            response.setAppliedCupon(purchase.getAppliedCoupon(), purchase.getFinalValue());
        }

        return response;
    }
}
