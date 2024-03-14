package br.com.brunno.bookstore.paymentFlow.validator;

import br.com.brunno.bookstore.coupon.Coupon;
import br.com.brunno.bookstore.coupon.CouponRepository;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import br.com.brunno.bookstore.paymentflow.validator.CouponValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CouponValidatorTest {

    private static final Coupon coupon = new Coupon("some code", .10, LocalDate.now().minusDays(1));

    CouponRepository couponRepository = mock(CouponRepository.class);
    CouponValidator couponValidator = new CouponValidator(couponRepository);


    @DisplayName("Rejeita quando o cupon estiver expirado")
    @Test
    void expiredCoupon() {
        when(couponRepository.findByCode("some code")).thenReturn(Optional.of(coupon));
        NewPurchaseRequest request = new NewPurchaseRequest();
        ReflectionTestUtils.setField(request, "coupon", "some code");
        Errors errors = mock(Errors.class);

        couponValidator.validate(request, errors);

        verify(errors, times(1)).rejectValue(eq("coupon"), any(), eq("coupon expired"));
    }

    @DisplayName("quando não for passado cupom na request, então cupom não deve ser validado")
    @Test
    void withoutCoupon() {
        NewPurchaseRequest request = new NewPurchaseRequest();
        Errors errors = mock(Errors.class);

        couponValidator.validate(request, errors);

        Assertions.assertThat(errors.hasErrors()).isFalse();
    }

    @DisplayName("Rejeita quando o copom passado na request não existir")
    @Test
    void couponNotFound() {
        when(couponRepository.findByCode("some code")).thenReturn(Optional.empty());
        NewPurchaseRequest request = new NewPurchaseRequest();
        ReflectionTestUtils.setField(request, "coupon", "some code");
        Errors errors = mock(Errors.class);

        couponValidator.validate(request, errors);

        verify(errors, times(1)).rejectValue(eq("coupon"), any(), eq("coupon not found"));
    }

    @DisplayName("Cupom valido quando existir e não estiver expirado")
    @Test
    void validCoupon() {
        when(couponRepository.findByCode("some code")).thenReturn(Optional.of(coupon));
        NewPurchaseRequest request = new NewPurchaseRequest();
        ReflectionTestUtils.setField(request, "coupon", "some code");
        Errors errors = mock(Errors.class);

        couponValidator.validate(request, errors);

        Assertions.assertThat(errors.hasErrors()).isFalse();
    }
}
