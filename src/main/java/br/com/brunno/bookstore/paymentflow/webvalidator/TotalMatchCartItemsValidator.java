package br.com.brunno.bookstore.paymentflow.webvalidator;

import br.com.brunno.bookstore.book.BookRepository;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import br.com.brunno.bookstore.paymentflow.Order;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class TotalMatchCartItemsValidator implements Validator {

    private final BookRepository bookRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return NewPurchaseRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        NewPurchaseRequest request = (NewPurchaseRequest) target;

        Order order = request.getOrder();

        if (!order.calculateTotalFromItems(bookRepository).equals(order.getTotal())) {
            errors.rejectValue("order.total", null, "'total' field doesn't match the total value in 'items'");
        }

    }
}
