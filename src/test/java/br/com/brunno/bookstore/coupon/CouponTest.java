package br.com.brunno.bookstore.coupon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CouponTest {

    @Test
    @DisplayName("cupom não expirado quando a data de expiracao estiver no futuro")
    void futureDate() {
        Coupon coupon = new Coupon("test123", .5, LocalDate.now().plusDays(1));

        Assertions.assertFalse(coupon.isExpired());
    }

    @Test
    @DisplayName("cupom não expirado quando a data estiver no presente")
    void presentDate() {
        Coupon coupon = new Coupon("test123", .5, LocalDate.now());

        Assertions.assertFalse(coupon.isExpired());
    }

    @Test
    @DisplayName("cupom expirado quando a data estiver no passado")
    void pastDate() {
        Coupon coupon = new Coupon("test123", .5, LocalDate.now().minusDays(1));

        Assertions.assertTrue(coupon.isExpired());
    }

}
