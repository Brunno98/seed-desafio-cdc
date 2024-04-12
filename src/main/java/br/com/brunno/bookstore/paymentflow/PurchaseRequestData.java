package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.country.CountryRepository;
import br.com.brunno.bookstore.coupon.CouponRepository;
import br.com.brunno.bookstore.state.StateRepository;

import java.util.Optional;

public interface PurchaseRequestData {

    Purchase toPurchase(CountryRepository countryRepository, CouponRepository couponRepository, StateRepository stateRepository);

    Optional<String> getCouponCode();

    Long getCountryId();

    Long getStateId();

    Order getOrder();
}
