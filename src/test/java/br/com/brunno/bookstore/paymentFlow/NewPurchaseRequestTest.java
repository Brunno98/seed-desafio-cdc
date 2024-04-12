package br.com.brunno.bookstore.paymentFlow;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.country.CountryRepository;
import br.com.brunno.bookstore.coupon.Coupon;
import br.com.brunno.bookstore.coupon.CouponRepository;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import br.com.brunno.bookstore.state.State;
import br.com.brunno.bookstore.state.StateRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import javax.swing.text.html.Option;
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
    CountryRepository countryRepository = Mockito.mock(CountryRepository.class);
    StateRepository stateRepository = Mockito.mock(StateRepository.class);

    Country brazil = new Country("Brazil");
    State rioDeJaneiro = new State("Rio de janeiro", brazil);
    Coupon coupon = new Coupon("test123", .5, LocalDate.now().plusDays(1));
    NewPurchaseRequest purchaseRequest = new NewPurchaseRequest();

    @Test
    @DisplayName("Quando pais de compra tiver estado, então estado deve ser adicionado a compra")
    void testState() {
        ReflectionTestUtils.setField(brazil, "states", List.of(rioDeJaneiro));
        Mockito.doReturn(Optional.of(brazil)).when(countryRepository).findById(1L);
        Mockito.doReturn(Optional.of(rioDeJaneiro)).when(stateRepository).findById(1L);
        ReflectionTestUtils.setField(purchaseRequest, "countryId", 1L);
        ReflectionTestUtils.setField(purchaseRequest, "stateId", 1L);

        purchaseRequest.toPurchase(countryRepository, couponRepository, stateRepository);

        verify(stateRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Quando pais de compra não tiver estado, purchase não deve ter estado")
    void testNotState() {
        doReturn(Optional.of(brazil)).when(countryRepository).findById(1L);
        ReflectionTestUtils.setField(purchaseRequest, "countryId", 1L);

        purchaseRequest.toPurchase(countryRepository, couponRepository, stateRepository);

        verify(entityManager, never()).find(Mockito.eq(State.class), Mockito.any());
    }

    @Test
    @DisplayName("Quando request tiver cupom, então o coupom deve ser aplicado na compra")
    void testCoupom() {
        doReturn(Optional.of(brazil)).when(countryRepository).findById(1L);
        ReflectionTestUtils.setField(purchaseRequest, "countryId", 1L);
        doReturn(Optional.of(coupon)).when(couponRepository).findByCode("test123");
        ReflectionTestUtils.setField(purchaseRequest, "coupon", "test123");

        purchaseRequest.toPurchase(countryRepository, couponRepository, stateRepository);

        verify(couponRepository, times(1)).findByCode("test123");
    }

    @Test
    @DisplayName("Quando request não tiver cupom, então a compra não terá cupom")
    void testNotCoupon() {
        doReturn(Optional.of(brazil)).when(countryRepository).findById(1L);
        ReflectionTestUtils.setField(purchaseRequest, "countryId", 1L);

        purchaseRequest.toPurchase(countryRepository, couponRepository, stateRepository);

        verify(couponRepository, never()).findByCode(Mockito.any());
    }


}
