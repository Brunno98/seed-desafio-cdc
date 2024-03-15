package br.com.brunno.bookstore.coupon;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.Clock;
import java.time.LocalDate;

@Getter
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String code;

    @Positive
    private double discount;

    @NotNull
    private LocalDate expirationDate;

    @Deprecated
    public Coupon() {}

    public Coupon(String code, double discount, LocalDate expirationDate) {
        this.code = code;
        this.discount = discount;
        this.expirationDate = expirationDate;
    }

    public boolean isExpired() {
        LocalDate now = LocalDate.now();

        return now.isAfter(expirationDate);
    }
}
