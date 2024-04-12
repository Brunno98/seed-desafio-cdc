package br.com.brunno.bookstore.paymentflow.usecasevalidator;

import br.com.brunno.bookstore.coupon.Coupon;
import br.com.brunno.bookstore.coupon.CouponRepository;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import br.com.brunno.bookstore.paymentflow.PurchaseRequestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

class CouponValidatorUseCaseTest {

    CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
    CouponValidatorUseCase couponValidatorUseCase = new CouponValidatorUseCase(couponRepository);

    Coupon coupon = new Coupon("couponCode", 0.1, LocalDate.now().plusDays(1));

    @DisplayName("Quando coupon for invalido então deve chamar o invalidate handler")
    @Test
    void invalid() {
        PurchaseRequestData purchaseRequestData = new NewPurchaseRequest();
        ReflectionTestUtils.setField(purchaseRequestData, "coupon", "couponCode");
        AtomicBoolean called = new AtomicBoolean(false);

        couponValidatorUseCase.validate(purchaseRequestData, () -> called.set(true));

        Assertions.assertThat(called).isTrue();
    }

    @DisplayName("Quando coupon for valido então deve o invalidate handler não deve ser chamado")
    @Test
    void valid() {
        PurchaseRequestData purchaseRequestData = new NewPurchaseRequest();
        ReflectionTestUtils.setField(purchaseRequestData, "coupon", "couponCode");
        Mockito.doReturn(Optional.of(coupon)).when(couponRepository).findByCode("couponCode");

        couponValidatorUseCase.validate(purchaseRequestData,
                () -> Assertions.fail("invalidate handler não deveria ser chamado"));
    }
}