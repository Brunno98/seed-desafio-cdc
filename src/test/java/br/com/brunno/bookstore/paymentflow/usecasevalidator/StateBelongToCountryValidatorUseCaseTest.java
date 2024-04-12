package br.com.brunno.bookstore.paymentflow.usecasevalidator;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.country.CountryRepository;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import br.com.brunno.bookstore.paymentflow.PurchaseRequestData;
import br.com.brunno.bookstore.state.State;
import br.com.brunno.bookstore.state.StateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class StateBelongToCountryValidatorUseCaseTest {
    StateRepository stateRepository = mock(StateRepository.class);
    CountryRepository countryRepository = mock(CountryRepository.class);
    StateBelongToCountryValidatorUseCase stateBelongToCountryValidatorUseCase =
            new StateBelongToCountryValidatorUseCase(countryRepository, stateRepository);
    PurchaseRequestData purchaseRequestData = new NewPurchaseRequest();

    //pais não existe -> lança exceçao
    //pais existe mas nao tem estado -> valido
    //pais existe, tem estado mas estado nao existe -> lança excecao
    //pais existe, tem estado e estado pertence ao pais -> valido
    //pais existe, tem estado, estado existe mas estado nao pertence ao pais -> invalido

    @DisplayName("Caso não encotre pais então lanca excecao")
    @Test
    void countryNotFound() {
        ReflectionTestUtils.setField(purchaseRequestData, "countryId", 1L);
        doReturn(Optional.empty()).when(countryRepository).findById(1L);

        Assertions.assertThrows(IllegalStateException.class, () ->
            stateBelongToCountryValidatorUseCase.validate(purchaseRequestData, Assertions::fail)
        );
    }

    @DisplayName("Caso pais não tenha estado, então é valido")
    @Test
    void countryDoesNotHaveState() {
        ReflectionTestUtils.setField(purchaseRequestData, "countryId", 1L);
        Country country = new Country("Brazil");
        doReturn(Optional.of(country)).when(countryRepository).findById(1L);

        stateBelongToCountryValidatorUseCase.validate(purchaseRequestData, Assertions::fail);

        Assertions.assertFalse(country.hasState());
    }

    @DisplayName("Caso estado não exista, então lanca excecao")
    @Test
    void stateNotFound() {
        ReflectionTestUtils.setField(purchaseRequestData, "countryId", 1L);
        Country country = new Country("Brazil");
        State state = new State("Rio De Janeiro", country);
        ReflectionTestUtils.setField(country, "states", List.of(state));
        doReturn(Optional.of(country)).when(countryRepository).findById(1L);
        ReflectionTestUtils.setField(purchaseRequestData, "stateId", 1L);
        doReturn(Optional.empty()).when(stateRepository).findById(1L);

        Assertions.assertThrows(IllegalStateException.class, () ->
                stateBelongToCountryValidatorUseCase.validate(purchaseRequestData, Assertions::fail)
        );

    }

    @DisplayName("Caso estado não pertenca ao pais então é invalido")
    @Test
    void invalid() {
        ReflectionTestUtils.setField(purchaseRequestData, "countryId", 1L);
        Country country = new Country("Brazil");
        State state = new State("Rio De Janeiro", country);
        ReflectionTestUtils.setField(country, "states", List.of(state));
        doReturn(Optional.of(country)).when(countryRepository).findById(1L);
        ReflectionTestUtils.setField(purchaseRequestData, "stateId", 1L);
        doReturn(Optional.of(new State("Buenos Aires", new Country("Argentina")))).when(stateRepository).findById(1L);

        AtomicBoolean called = new AtomicBoolean(false);
        stateBelongToCountryValidatorUseCase.validate(purchaseRequestData, () -> called.set(true));

        Assertions.assertTrue(called.get());
    }

    @DisplayName("Caso estado pertenca ao pais então é válido")
    @Test
    void valid() {
        ReflectionTestUtils.setField(purchaseRequestData, "countryId", 1L);
        Country country = new Country("Brazil");
        State state = new State("Rio De Janeiro", country);
        ReflectionTestUtils.setField(country, "states", List.of(state));
        ReflectionTestUtils.setField(purchaseRequestData, "stateId", 1L);
        doReturn(Optional.of(country)).when(countryRepository).findById(1L);
        doReturn(Optional.of(state)).when(stateRepository).findById(1L);

        stateBelongToCountryValidatorUseCase.validate(purchaseRequestData, Assertions::fail);

        Assertions.assertTrue(country.hasState());
    }

}