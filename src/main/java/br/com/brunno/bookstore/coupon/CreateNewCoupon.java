package br.com.brunno.bookstore.coupon;

import org.springframework.stereotype.Service;

@Service
public class CreateNewCoupon {

    private final CouponRepository couponRepository;

    public CreateNewCoupon(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon execute(NewCouponDto newCouponDto) {
        Coupon coupon = newCouponDto.toCoupon();
        couponRepository.save(coupon);
        return coupon;
    }
}
