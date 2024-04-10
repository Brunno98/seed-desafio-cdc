package br.com.brunno.bookstore.country;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NewCountryController {

    private final CreateNewCountry createNewCountry;

    @Transactional
    @PostMapping("/country")
    public NewCountryResponse createCountry(@RequestBody @Valid NewCountryRequest newCountryRequest) {
        Country country = createNewCountry.create(newCountryRequest);

        return new NewCountryResponse(country);
    }
}
