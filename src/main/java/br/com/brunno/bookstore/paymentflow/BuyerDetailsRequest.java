package br.com.brunno.bookstore.paymentflow;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.shared.validator.Document;
import br.com.brunno.bookstore.shared.validator.IdExists;
import br.com.brunno.bookstore.state.State;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BuyerDetailsRequest {

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

}
