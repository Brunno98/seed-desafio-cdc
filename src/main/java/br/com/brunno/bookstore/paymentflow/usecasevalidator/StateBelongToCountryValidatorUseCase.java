package br.com.brunno.bookstore.paymentflow.usecasevalidator;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.country.CountryRepository;
import br.com.brunno.bookstore.paymentflow.PurchaseRequestData;
import br.com.brunno.bookstore.state.State;
import br.com.brunno.bookstore.state.StateRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StateBelongToCountryValidatorUseCase {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;

    public StateBelongToCountryValidatorUseCase(CountryRepository countryRepository, StateRepository stateRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
    }

    /*
    a - contry exists
    b - contry does not have state
    c - state exists
    d - state belong to contry
    r - invalid or error

    a && (b || (c && d))

    a b c d  =
    v v v v  v  1
    v v v f  v  2
    v v f v  v  3
    v v f f  v  4
    v f v v  v  5
    v f v f  f  6
    v f f v  f  7
    v f f f  f  8
    f v v v  f  9
    f v v f  f  10
    f v f v  f  11
    f v f f  f  12
    f f v v  f  13
    f f v f  f  14
    f f f v  f  15
    f f f f  f  16

    a - (1,9), (2,10), (3,11), (4,12), (5,13)
    b - (2,6), (3,7), (4, 8)
    c - (5,7)
    d - (5,6)

    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13

    [1, 9, 6, 7, 5]

    ---
    a - (1,9), (5,13), (6,14), (7,15), (8, 16)
    b - (2,6), (3,7), (4,8)
    c - (1,3)
    d - (1,2)
    1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 16
     */

    public void validate(PurchaseRequestData purchaseRequestData, Runnable invalidateHandler) {
        Optional<Country> optionalCountry = countryRepository.findById(purchaseRequestData.getCountryId());
        if (optionalCountry.isEmpty()) throw new IllegalStateException("Expected to find country with id "+purchaseRequestData.getCountryId());
        Country country = optionalCountry.get();
        if (!country.hasState()) return;

        Optional<State> optionalState = stateRepository.findById(purchaseRequestData.getStateId());
        if (optionalState.isEmpty()) throw new IllegalStateException("Expected to find state with id "+purchaseRequestData.getStateId());
        State state = optionalState.get();

        if (!state.belongTo(country)) invalidateHandler.run();
    }

}
