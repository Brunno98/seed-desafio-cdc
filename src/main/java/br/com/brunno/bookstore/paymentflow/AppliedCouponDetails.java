package br.com.brunno.bookstore.paymentflow;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AppliedCouponDetails {
    private final String code;
    private final double appliedDiscount;
    private final LocalDate expirationDate;

    public AppliedCouponDetails(AppliedCoupon coupon) {
        this.code = coupon.getCode();
        this.appliedDiscount = coupon.getAppliedDiscount();
        this.expirationDate = coupon.getExpireDateWhenApplied();
    }
}
