package br.com.brunno.bookstore.coupon;

import br.com.brunno.bookstore.shared.validator.UniqueValue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Getter
public class NewCouponRequest implements NewCouponDto {

    @NotBlank
    @UniqueValue(domainClass = Coupon.class, fieldName = "code")
    private String code;

    @Positive
    @Range(min = 0, max = 1)
    private double discount;

    @NotNull
    @Future
    private LocalDate expirationDate;

    @Override
    public Coupon toCoupon() {
        return new Coupon(code, discount, expirationDate);
    }
}
