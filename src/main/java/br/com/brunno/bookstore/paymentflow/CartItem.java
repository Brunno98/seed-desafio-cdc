package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.book.Book;
import br.com.brunno.bookstore.shared.validator.IdExists;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Embeddable
@Getter
public class CartItem {
    @NotNull
    @IdExists(domain = Book.class)
    private Integer bookId;

    @NotNull
    @Positive
    private Integer quantity;
}
