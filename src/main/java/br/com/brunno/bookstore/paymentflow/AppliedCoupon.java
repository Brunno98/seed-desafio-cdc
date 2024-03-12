package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.coupon.Coupon;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Embeddable
public class AppliedCoupon {

    @ManyToOne
    private Coupon coupon;
    private double appliedDiscount;
    private LocalDate expireDateWhenApplied;

    @Deprecated
    public AppliedCoupon() {}

    public AppliedCoupon(Coupon coupon) {
        this.coupon = coupon;
        this.appliedDiscount = coupon.getDiscount();
        this.expireDateWhenApplied = coupon.getExpirationDate();
    }

    public String getCode() {
        return coupon.getCode();
    }
}
