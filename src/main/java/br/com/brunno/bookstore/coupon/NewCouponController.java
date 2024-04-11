package br.com.brunno.bookstore.coupon;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NewCouponController {

    private final CreateNewCoupon createNewCoupon;

    @PostMapping("/coupon")
    public NewCouponResponse createCoupon(@RequestBody @Valid NewCouponRequest newCouponRequest) {
        Coupon coupon = createNewCoupon.execute(newCouponRequest);

        return new NewCouponResponse(coupon);
    }
}
