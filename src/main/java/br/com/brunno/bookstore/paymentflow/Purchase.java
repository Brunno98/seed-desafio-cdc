package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.coupon.Coupon;
import br.com.brunno.bookstore.state.State;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.util.Assert;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;

    private String name;

    private String lastName;

    private String document;

    private String address;

    private String adjunct;

    private String city;

    private String phoneNumber;

    private String cep;

    @ManyToOne
    private Country country;

    @ManyToOne
    private State state;

    private Order order;

    private AppliedCoupon appliedCoupon;

    @Deprecated
    public Purchase() {}

    public Purchase(
            String email,
            String name,
            String lastName,
            String document,
            String address,
            String adjunct,
            String city,
            String phoneNumber,
            String cep,
            Country country,
            Order order) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.document = document;
        this.address = address;
        this.adjunct = adjunct;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.cep = cep;
        this.country = country;
        this.order = order;
    }

    public String getId() {
        return this.id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDocument() {
        return document;
    }

    public String getAddress() {
        return address;
    }

    public String getAdjunct() {
        return adjunct;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCep() {
        return cep;
    }

    public String getCountryName() {
        return this.country.getName();
    }

    @Nullable
    public String getStateName() {
        if (this.state == null) return null;
        return this.state.getName();
    }

    public Order getOrder() {
        return this.order;
    }

    public void setState(State state) {
        Assert.state(state.belongTo(country), "state must belong to country in purchase");
        this.state = state;
    }

    @Nullable
    public AppliedCoupon getAppliedCoupon() {
        return this.appliedCoupon;
    }

    public void applyCoupon(Coupon coupon) {
        Assert.notNull(coupon, "Cannot apply to purchaes a null as cupon");
        Assert.isTrue(!this.hasAppliedCoupon(), "Purchases with applied cupon should not apply other");
        this.appliedCoupon = new AppliedCoupon(coupon);
    }

    public boolean hasAppliedCoupon() {
        return appliedCoupon != null;
    }
}
