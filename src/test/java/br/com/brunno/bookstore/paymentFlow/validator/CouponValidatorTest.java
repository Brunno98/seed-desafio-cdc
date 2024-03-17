package br.com.brunno.bookstore.paymentFlow.validator;

import br.com.brunno.bookstore.coupon.Coupon;
import br.com.brunno.bookstore.coupon.CouponRepository;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import br.com.brunno.bookstore.paymentflow.validator.CouponValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CouponValidatorTest {

    CouponRepository couponRepository = mock(CouponRepository.class);
    CouponValidator couponValidator = new CouponValidator(couponRepository);

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

    @DisplayName("Rejeita quando o cupon estiver expirado")
    @Test
    void expiredCoupon() {
        Coupon expiredCoupon = new Coupon("some code", .10, LocalDate.now().minusDays(1));
        when(couponRepository.findByCode("some code")).thenReturn(Optional.of(expiredCoupon));
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

        verify(couponRepository, never()).findByCode(any());
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
        Coupon coupon = new Coupon("some code", .10, LocalDate.now().plusDays(1));
        when(couponRepository.findByCode("some code")).thenReturn(Optional.of(coupon));
        NewPurchaseRequest request = new NewPurchaseRequest();
        ReflectionTestUtils.setField(request, "coupon", "some code");
        Errors errors = mock(Errors.class);

        couponValidator.validate(request, errors);

        verify(errors, never()).rejectValue(eq("coupon"), any(), any());
    }

    @DisplayName("Se já existir erro, então não faz a validacao de cupom")
    @Test
    void hasErrors() {
        NewPurchaseRequest request = new NewPurchaseRequest();
        Errors errors = Mockito.mock(Errors.class);
        doReturn(true).when(errors).hasErrors();

        couponValidator.validate(request, errors);

        verify(couponRepository, never()).findByCode(any());
        verify(errors, never()).rejectValue(eq("coupon"), any(), any());
    }
}
