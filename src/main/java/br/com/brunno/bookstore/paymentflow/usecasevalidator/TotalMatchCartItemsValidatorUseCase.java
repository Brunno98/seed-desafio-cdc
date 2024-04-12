package br.com.brunno.bookstore.paymentflow.usecasevalidator;

import br.com.brunno.bookstore.book.BookRepository;
import br.com.brunno.bookstore.paymentflow.Order;
import br.com.brunno.bookstore.paymentflow.PurchaseRequestData;
import org.springframework.stereotype.Component;

@Component
public class TotalMatchCartItemsValidatorUseCase {

    private final BookRepository bookRepository;

    public TotalMatchCartItemsValidatorUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void validate(PurchaseRequestData purchaseRequestData, Runnable invalidateHandler) {
        Order order = purchaseRequestData.getOrder();

        if (!order.calculateTotalFromItems(bookRepository).equals(order.getTotal())) {
            invalidateHandler.run();
        }
    }
}
