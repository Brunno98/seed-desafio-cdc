package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.country.CountryRepository;
import br.com.brunno.bookstore.coupon.CouponRepository;
import br.com.brunno.bookstore.paymentflow.usecasevalidator.CouponValidatorUseCase;
import br.com.brunno.bookstore.paymentflow.usecasevalidator.StateBelongToCountryValidatorUseCase;
import br.com.brunno.bookstore.paymentflow.usecasevalidator.TotalMatchCartItemsValidatorUseCase;
import br.com.brunno.bookstore.state.StateRepository;
import org.springframework.stereotype.Service;

@Service
public class InitiatePurchaseService {

    private final CouponRepository couponRepository;
    private final PurchaseRepository purchaseRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CouponValidatorUseCase couponValidator;
    private final StateBelongToCountryValidatorUseCase stateBelongToCountryValidator;
    private final TotalMatchCartItemsValidatorUseCase totalMatchCartItemsValidator;

    public InitiatePurchaseService(CouponRepository couponRepository, PurchaseRepository purchaseRepository, CountryRepository countryRepository, StateRepository stateRepository, CouponValidatorUseCase couponValidator, StateBelongToCountryValidatorUseCase stateBelongToCountryValidator, TotalMatchCartItemsValidatorUseCase totalMatchCartItemsValidator) {
        this.couponRepository = couponRepository;
        this.purchaseRepository = purchaseRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.couponValidator = couponValidator;
        this.stateBelongToCountryValidator = stateBelongToCountryValidator;
        this.totalMatchCartItemsValidator = totalMatchCartItemsValidator;
    }


    public Purchase execute(PurchaseRequestData purchaseRequestData) {
        couponValidator.validate(purchaseRequestData, () -> {
            throw new IllegalArgumentException("Coupon is invalid");
        });

        stateBelongToCountryValidator.validate(purchaseRequestData, () -> {
            throw new IllegalArgumentException("State does not belong to contry");
        });

        totalMatchCartItemsValidator.validate(purchaseRequestData, () -> {
            throw new IllegalArgumentException("Total of order does not match de sum of items value");
        });

        Purchase purchase = purchaseRequestData.toPurchase(countryRepository, couponRepository, stateRepository);
        return purchaseRepository.save(purchase);
    }
}
