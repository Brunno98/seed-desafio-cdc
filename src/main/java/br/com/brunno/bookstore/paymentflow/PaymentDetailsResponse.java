package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.state.State;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.util.List;

@Getter
public class PaymentDetailsResponse {
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
    private Integer total;
    private List<CartItem> items;

    public PaymentDetailsResponse(Payment payment) {
        this.id = payment.getId();
        this.email = payment.getEmail();
        this.name = payment.getName();
        this.lastName = payment.getLastName();
        this.document = payment.getDocument();
        this.address = payment.getAddress();
        this.adjunct = payment.getAdjunct();
        this.city = payment.getCity();
        this.phoneNumber = payment.getPhoneNumber();
        this.cep = payment.getCep();
        this.countryName = payment.getCountryName();
        this.stateName = payment.getStateName();
        this.total = payment.getTotal();
        this.items = payment.getItems();
    }
}
