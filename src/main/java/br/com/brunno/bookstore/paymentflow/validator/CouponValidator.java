package br.com.brunno.bookstore.paymentflow.validator;

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

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        NewPurchaseRequest request = (NewPurchaseRequest) target;

        if (!request.hasCoupon()) return;

        Optional<Coupon> optionalCoupon = couponRepository.findByCode(request.getCoupon());

        if (optionalCoupon.isEmpty()) {
            errors.rejectValue("coupon", null, "coupon not found");
            return;
        }

        Coupon coupon = optionalCoupon.get();

        if (coupon.isExpired()) {
            errors.rejectValue("coupon", null, "coupon expired");
        }
    }
}
