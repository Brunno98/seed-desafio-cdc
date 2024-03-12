package br.com.brunno.bookstore.paymentflow;

import lombok.Getter;
import org.springframework.util.Assert;

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
    private AppliedCouponDetails coupon;

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
        if (purchase.hasAppliedCoupon()){
            Assert.notNull(purchase.getAppliedCoupon(), "getAppliedCoupon() should not be null because hasAppliedCoupon() was returned true.");
            this.coupon = new AppliedCouponDetails(purchase.getAppliedCoupon());
        }
    }
}
