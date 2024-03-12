package br.com.brunno.bookstore.paymentflow.validator;

import br.com.brunno.bookstore.book.Book;
import br.com.brunno.bookstore.paymentflow.Order;
import br.com.brunno.bookstore.paymentflow.OrderItem;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
//TODO: Total must be the value and not the quantity!!
@Component
public class TotalMatchCartItemsValidator implements Validator {

    private final EntityManager entityManager;

    @Override
    public boolean supports(Class<?> clazz) {
        return NewPurchaseRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        NewPurchaseRequest request = (NewPurchaseRequest) target;

        Order order = request.getOrder();

        List<BigDecimal> collect = order.getItems().stream().map(item -> {
            Book book = entityManager.find(Book.class, item.getBookId());
            BigDecimal price = book.getPrice();
            return price.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).collect(Collectors.toList());

        BigDecimal reduced = collect.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        if (!reduced.equals(order.getTotal())) {
            errors.rejectValue("order.total", null, "'total' field doesn't match the total value in 'items'");
        }

    }
}
