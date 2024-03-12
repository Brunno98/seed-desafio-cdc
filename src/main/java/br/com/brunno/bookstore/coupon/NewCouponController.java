package br.com.brunno.bookstore.coupon;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NewCouponController {

    private final EntityManager entityManager;

    @Transactional
    @PostMapping("/coupon")
    public NewCouponResponse createCoupon(@RequestBody @Valid NewCouponRequest newCouponRequest) {
        Coupon coupon = newCouponRequest.toDomain();

        entityManager.persist(coupon);

        return new NewCouponResponse(coupon);
    }
}
