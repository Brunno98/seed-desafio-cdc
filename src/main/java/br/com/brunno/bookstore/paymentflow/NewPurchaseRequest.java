package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.country.CountryRepository;
import br.com.brunno.bookstore.coupon.Coupon;
import br.com.brunno.bookstore.coupon.CouponRepository;
import br.com.brunno.bookstore.shared.validator.Document;
import br.com.brunno.bookstore.shared.validator.IdExists;
import br.com.brunno.bookstore.state.State;
import br.com.brunno.bookstore.state.StateRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Getter
public class NewPurchaseRequest implements PurchaseRequestData {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    @Document
    private String document;

    @NotBlank
    private String address;

    @NotBlank
    private String adjunct;

    @NotBlank
    private String city;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String cep;

    @NotNull
    @IdExists(domain = Country.class)
    private Long countryId;

    @IdExists(domain = State.class)
    private Long stateId;

    @NotNull
    @Valid
    private Order order;

    private String coupon;


    public boolean hasCoupon() {
        return StringUtils.hasText(coupon);
    }

    @Override
    public Purchase toPurchase(CountryRepository countryRepository, CouponRepository couponRepository, StateRepository stateRepository) {
        Optional<Country> optionalCountry = countryRepository.findById(countryId);
        Assert.isTrue(optionalCountry.isPresent(), "NewPurchaseRequest should not exists with an countryId inexistent!");
        Country country = optionalCountry.get();
        Purchase purchase = new Purchase(
                email,
                name,
                lastName,
                document,
                address,
                adjunct,
                city,
                phoneNumber,
                cep,
                country,
                order
        );
        if (country.hasState()) {
            Optional<State> optionalState = stateRepository.findById(stateId);
            Assert.isTrue(optionalState.isPresent(), "State with "+stateId+" should exists");
            purchase.setState(optionalState.get());
        }

        if (hasCoupon()) {
            Optional<Coupon> optionalCoupon = couponRepository.findByCode(coupon);
            Assert.state(optionalCoupon.isPresent(), "Should not be possible to convert purchaseRequest to domain with inexistent coupon code");
            purchase.applyCoupon(optionalCoupon.get());
        }

        return purchase;
    }

    @Override
    public Optional<String> getCouponCode() {
        return Optional.ofNullable(coupon);
    }
}
