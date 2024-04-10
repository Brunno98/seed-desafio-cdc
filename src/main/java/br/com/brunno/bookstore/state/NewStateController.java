package br.com.brunno.bookstore.state;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NewStateController {

    private final CreateNewState createNewState;

    @Transactional
    @PostMapping("/state")
    public NewStateResponse createState(@RequestBody @Valid NewStateRequest newStateRequest) {
        State state = createNewState.create(newStateRequest);

        return new NewStateResponse(state);
    }
}
