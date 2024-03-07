package br.com.brunno.bookstore.author;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@RequiredArgsConstructor
public class CreateAuthorRequest {

//    @NotBlank
    private final String name;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Length(max = 400)
    private final String description;

    public Author toDomain() {
        return new Author(name, email, description);
    }
}
