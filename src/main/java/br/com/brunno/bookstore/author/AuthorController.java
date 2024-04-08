package br.com.brunno.bookstore.author;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final CreateNewAuthor createNewAuthor;

    @Autowired
    public AuthorController(CreateNewAuthor createNewAuthor) {
        this.createNewAuthor = createNewAuthor;
    }

    @PostMapping
    public ResponseEntity<CreateAuthorResponse> createAuthor(@RequestBody @Valid CreateAuthorRequest createRequest) {
        Author author = createNewAuthor.create(createRequest);

        CreateAuthorResponse response = CreateAuthorResponse.from(author) ;

        return ResponseEntity.ok(response);
    }
}
