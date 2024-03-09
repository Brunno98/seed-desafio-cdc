package br.com.brunno.bookstore.state;

import lombok.Getter;

@Getter
public class NewStateResponse {

    private final Long id;
    private final String name;
    private final String country;

    public NewStateResponse(State state) {
        this.id = state.getId();
        this.name = state.getName();
        this.country = state.getCountryName();
    }
}
