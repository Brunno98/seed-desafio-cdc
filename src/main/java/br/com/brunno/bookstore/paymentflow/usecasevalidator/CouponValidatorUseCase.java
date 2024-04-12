package br.com.brunno.bookstore.paymentflow.usecasevalidator;

import br.com.brunno.bookstore.coupon.Coupon;
import br.com.brunno.bookstore.coupon.CouponRepository;
import br.com.brunno.bookstore.paymentflow.PurchaseRequestData;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CouponValidatorUseCase {

    private final CouponRepository couponRepository;

    public CouponValidatorUseCase(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void validate(PurchaseRequestData purchaseRequestData, Runnable invalidateHandler) {
        Optional<String> optionalCouponCode = purchaseRequestData.getCouponCode();
        optionalCouponCode.ifPresent(couponCode -> {
            Optional<Coupon> optionalCoupon = couponRepository.findByCode(couponCode);
            if (optionalCoupon.isEmpty()) invalidateHandler.run();
        });
    }

}
