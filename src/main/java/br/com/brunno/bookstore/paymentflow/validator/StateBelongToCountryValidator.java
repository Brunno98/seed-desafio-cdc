package br.com.brunno.bookstore.paymentflow.validator;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.paymentflow.PaymentRequest;
import br.com.brunno.bookstore.state.State;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class StateBelongToCountryValidator implements Validator {

    private final EntityManager entityManager;

    @Override
    public boolean supports(Class<?> clazz) {
        return PaymentRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        PaymentRequest request = (PaymentRequest) target;

        State state = entityManager.find(State.class, request.getStateId());
        Country country = entityManager.find(Country.class, request.getCountryId());

        if (!state.belongTo(country)) {
            errors.rejectValue("stateId", null, "state doesn't belong to country");
        }
    }
}
