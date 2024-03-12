package br.com.brunno.bookstore.coupon;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NewCouponResponse {

    private final Long id;
    private final String code;
    private final Double discount;
    private final LocalDate expirationDate;

    public NewCouponResponse(Coupon coupon) {
        this.id = coupon.getId();
        this.code = coupon.getCode();
        this.discount = coupon.getDiscount();
        this.expirationDate = coupon.getExpirationDate();
    }
}
