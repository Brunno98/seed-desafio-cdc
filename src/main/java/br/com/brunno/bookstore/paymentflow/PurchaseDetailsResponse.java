package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.coupon.Coupon;
import lombok.Getter;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Getter
public class PurchaseDetailsResponse {
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
    private String countryName;
    private String stateName;
    private Order order;
    private boolean appliedCupon;
    private AppliedCouponDetails coupon;
    private BigDecimal originalValue;
    private BigDecimal finalValue;

    public PurchaseDetailsResponse(Purchase purchase) {
        this.id = purchase.getId();
        this.email = purchase.getEmail();
        this.name = purchase.getName();
        this.lastName = purchase.getLastName();
        this.document = purchase.getDocument();
        this.address = purchase.getAddress();
        this.adjunct = purchase.getAdjunct();
        this.city = purchase.getCity();
        this.phoneNumber = purchase.getPhoneNumber();
        this.cep = purchase.getCep();
        this.countryName = purchase.getCountryName();
        this.stateName = purchase.getStateName();
        this.order = purchase.getOrder();
        this.originalValue = purchase.getOriginalValue();
        this.appliedCupon = purchase.hasAppliedCoupon();
    }

    public void setAppliedCupon(AppliedCoupon coupon, BigDecimal finalValue) {
        this.coupon = new AppliedCouponDetails(coupon);
        this.finalValue = finalValue;
    }
}
