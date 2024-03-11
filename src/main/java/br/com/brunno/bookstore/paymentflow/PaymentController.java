package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.paymentflow.validator.StateBelongToCountryValidator;
import br.com.brunno.bookstore.paymentflow.validator.TotalMatchCartItemsValidator;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

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
    @PostMapping("/payment")
    public String generatePayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        Payment payment = paymentRequest.toDomain(entityManager);
        entityManager.persist(payment);
        return "/payment/" + payment.getId();
    }

    @GetMapping("/payment/{id}")
    public PaymentDetailsResponse getPaymentDetails(@PathVariable String id) {
        Payment payment = entityManager.find(Payment.class, id);

        return new PaymentDetailsResponse(payment);
    }
}
