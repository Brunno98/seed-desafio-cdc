package br.com.brunno.bookstore.paymentflow.validator;

import br.com.brunno.bookstore.paymentflow.CartItem;
import br.com.brunno.bookstore.paymentflow.PaymentRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class TotalMatchCartItemsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PaymentRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        PaymentRequest request = (PaymentRequest) target;

        List<CartItem> items = request.getItems();
        int totalInCartItems = items.stream().mapToInt(CartItem::getQuantity).sum();

        if (request.getTotal() != totalInCartItems) {
            errors.rejectValue("total", null, "'total' field doesn't match the total in 'items'");
        }

    }
}
