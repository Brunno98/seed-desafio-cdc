package br.com.brunno.bookstore.paymentFlow;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.coupon.Coupon;
import br.com.brunno.bookstore.coupon.CouponRepository;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import br.com.brunno.bookstore.state.State;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class NewPurchaseRequestTest {

    EntityManager entityManager = Mockito.mock(EntityManager.class);
    CouponRepository couponRepository = Mockito.mock(CouponRepository.class);

    Country brazil = new Country("Brazil");
    State rioDeJaneiro = new State("Rio de janeiro", brazil);
    Coupon coupon = new Coupon("test123", .5, LocalDate.now().plusDays(1));
    NewPurchaseRequest purchaseRequest = new NewPurchaseRequest();

    @Test
    @DisplayName("Quando pais de compra tiver estado, então estado deve ser adicionado a compra")
    void testState() {
        ReflectionTestUtils.setField(brazil, "states", List.of(rioDeJaneiro));
        Mockito.doReturn(brazil).when(entityManager).find(Country.class, 1L);
        Mockito.doReturn(rioDeJaneiro).when(entityManager).find(State.class, 1L);
        ReflectionTestUtils.setField(purchaseRequest, "countryId", 1L);
        ReflectionTestUtils.setField(purchaseRequest, "stateId", 1L);

        purchaseRequest.toDomain(entityManager, couponRepository);

        verify(entityManager, times(1)).find(State.class, 1L);
    }

    @Test
    @DisplayName("Quando pais de compra não tiver estado, purchase não deve ter estado")
    void testNotState() {
        doReturn(brazil).when(entityManager).find(Country.class, 1L);
        ReflectionTestUtils.setField(purchaseRequest, "countryId", 1L);

        purchaseRequest.toDomain(entityManager, couponRepository);

        verify(entityManager, never()).find(Mockito.eq(State.class), Mockito.any());
    }

    @Test
    @DisplayName("Quando request tiver cupom, então o coupom deve ser aplicado na compra")
    void testCoupom() {
        doReturn(brazil).when(entityManager).find(Country.class, 1L);
        ReflectionTestUtils.setField(purchaseRequest, "countryId", 1L);
        doReturn(Optional.of(coupon)).when(couponRepository).findByCode("test123");
        ReflectionTestUtils.setField(purchaseRequest, "coupon", "test123");

        purchaseRequest.toDomain(entityManager, couponRepository);

        verify(couponRepository, times(1)).findByCode("test123");
    }

    @Test
    @DisplayName("Quando request não tiver cupom, então a compra não terá cupom")
    void testNotCoupon() {
        doReturn(brazil).when(entityManager).find(Country.class, 1L);
        ReflectionTestUtils.setField(purchaseRequest, "countryId", 1L);

        purchaseRequest.toDomain(entityManager, couponRepository);

        verify(couponRepository, never()).findByCode(Mockito.any());
    }


}
