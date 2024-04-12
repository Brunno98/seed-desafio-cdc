package br.com.brunno.bookstore.paymentflow.webvalidator;

import br.com.brunno.bookstore.coupon.Coupon;
import br.com.brunno.bookstore.coupon.CouponRepository;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CouponValidator implements Validator {

    private final CouponRepository couponRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return NewPurchaseRequest.class.isAssignableFrom(clazz);
    }

    /*
    a - has errors
    b - has coupon
    c - find coupon
    d - coupon expired

    R - invalid coupon

    (and)
    a b c d = R
1   v v v v   f
2   v v v f   f
3   v v f v   f
4   v v f f   f
5   v f v v   f
6   v f v f   f
7   v f f v   f
8   v f f f   f
9   f v v v   f
10  f v v f   v
11  f v f v   f
12  f v f f   f
13  f f v v   f
14  f f v f   f
15  f f f v   f
16  f f f f   f

    a -> (2,10)
    b -> (10,14)
    c -> (10,12)
    d -> (10,9)

    (2, 9, 10, 12, 14)

     */

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return; // (a)

        NewPurchaseRequest request = (NewPurchaseRequest) target;

        if (!request.hasCoupon()) return; // (b)

        Optional<Coupon> optionalCoupon = couponRepository.findByCode(request.getCoupon());

        if (optionalCoupon.isEmpty()) { // (c)
            errors.rejectValue("coupon", null, "coupon not found");
            return;
        }

        Coupon coupon = optionalCoupon.get();

        if (coupon.isExpired()) { // (d)
            errors.rejectValue("coupon", null, "coupon expired");
        }
    }
}
