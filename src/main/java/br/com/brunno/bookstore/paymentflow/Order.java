package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.book.Book;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Embeddable
@Getter
public class Order {
    @NotNull
    @Positive
    private BigDecimal total;

    @ElementCollection
    @Size(min = 1)
    @Valid
    private List<OrderItem> items;

    /*
    Dada um list de orderItem, calcula o total desse itens

    lista com 1 item com 1 unidade
    lista com 1 item com 2 unidades
    lista com 2 itens com 2 unidades
     */

    public BigDecimal calculateTotalFromItems(EntityManager entityManager) {
        return items.stream()
                .map(item -> {
                    Book book = entityManager.find(Book.class, item.getBookId());
                    return book.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
