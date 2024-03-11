package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.state.State;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Payment {
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

    private Integer total;

    @ElementCollection
    private List<CartItem> items;

    @Deprecated
    public Payment() {}

    public Payment(
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
            State state,
            Integer total,
            List<CartItem> cartItems) {
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
        this.state = state;
        this.total = total;
        this.items = cartItems;
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

    public Integer getTotal() {
        return total;
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public String getCountryName() {
        return this.country.getName();
    }

    public String getStateName() {
        if (this.state == null) return null;
        return this.state.getName();
    }
}
