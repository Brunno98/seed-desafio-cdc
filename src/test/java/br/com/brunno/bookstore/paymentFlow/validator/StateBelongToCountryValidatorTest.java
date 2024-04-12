package br.com.brunno.bookstore.paymentFlow.validator;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import br.com.brunno.bookstore.paymentflow.webvalidator.StateBelongToCountryValidator;
import br.com.brunno.bookstore.state.State;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;

public class StateBelongToCountryValidatorTest {

    EntityManager entityManager = Mockito.mock(EntityManager.class);

    StateBelongToCountryValidator stateBelongToCountryValidator = new StateBelongToCountryValidator(entityManager);

    /*
    (a) country has state
    (b) state belong to country

    a && b  =
    v    v  valido
    v    f  invalido
    f    v  valido
    f    f  valido

    - quando pais tiver estado e estado pertencer ao pais então é valido
    - quando tiver pais mas o estado não pertencer ao pais então é invalido
    - quando o pais não tiver estado então é valido
     */

    @Test
    @DisplayName("quando pais tiver estado e estado pertencer ao pais então é valido")
    void hasCountryAndStateValid() {
        Country brazil = new Country("Brazil");
        State rioDeJaneiro = new State("Rio de Janeiro", brazil);
        ReflectionTestUtils.setField(brazil, "states", List.of(rioDeJaneiro));
        Mockito.doReturn(brazil).when(entityManager).find(Country.class, 1L);
        Mockito.doReturn(rioDeJaneiro).when(entityManager).find(State.class, 1L);

        NewPurchaseRequest request = new NewPurchaseRequest();
        ReflectionTestUtils.setField(request, "countryId", 1L);
        ReflectionTestUtils.setField(request, "stateId", 1L);
        Errors errors = new BeanPropertyBindingResult(request, "test");


        stateBelongToCountryValidator.validate(request, errors);


        Assertions.assertThat(errors.hasErrors()).isFalse();
    }

    @Test
    @DisplayName("quando tiver pais mas o estado não pertencer ao pais então é invalido")
    void stateDoesnNotBelongToCountry() {
        Country argentina = new Country("Argentina");
        ReflectionTestUtils.setField(argentina, "states", List.of(new State("Buenos Aires", argentina)));
        State rioDeJaneiro = new State("Rio de Janeiro", new Country("Brazil"));
        Mockito.doReturn(argentina).when(entityManager).find(Country.class, 1L);
        Mockito.doReturn(rioDeJaneiro).when(entityManager).find(State.class, 1L);

        NewPurchaseRequest request = new NewPurchaseRequest();
        ReflectionTestUtils.setField(request, "countryId", 1L);
        ReflectionTestUtils.setField(request, "stateId", 1L);
        Errors errors = new BeanPropertyBindingResult(request, "test");


        stateBelongToCountryValidator.validate(request, errors);


        Assertions.assertThat(errors.hasErrors()).isTrue();
        Assertions.assertThat(errors.getFieldError("stateId")).isNotNull();
    }

    @Test
    @DisplayName("quando o pais não tiver estado então é valido")
    void validWhenCountryNasState() {
        Country brazil = new Country("Brazil");
        Mockito.doReturn(brazil).when(entityManager).find(Country.class, 1L);
        NewPurchaseRequest request = new NewPurchaseRequest();
        ReflectionTestUtils.setField(request, "countryId", 1L);
        Errors errors = new BeanPropertyBindingResult(request, "test");

        stateBelongToCountryValidator.validate(request, errors);

        Assertions.assertThat(errors.hasErrors()).isFalse();
    }

    @DisplayName("Se já existir erro, então não faz a validacao de estado pertence ao pais")
    @Test
    void hasErrors() {
        NewPurchaseRequest request = new NewPurchaseRequest();
        Errors errors = Mockito.mock(Errors.class);
        doReturn(true).when(errors).hasErrors();

        stateBelongToCountryValidator.validate(request, errors);

        Mockito.verify(entityManager, never()).find(Mockito.eq(Country.class), Mockito.any());
    }

}
