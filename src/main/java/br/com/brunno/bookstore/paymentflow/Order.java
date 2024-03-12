package br.com.brunno.bookstore.paymentflow;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
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
}
