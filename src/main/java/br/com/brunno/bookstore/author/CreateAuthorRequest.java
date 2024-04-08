package br.com.brunno.bookstore.author;

import br.com.brunno.bookstore.shared.validator.UniqueValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@RequiredArgsConstructor
public class CreateAuthorRequest implements NewAuthorData{

    @NotBlank
    private final String name;

    @NotBlank
    @Email
    @UniqueValue(domainClass = Author.class, fieldName = "email")
    private final String email;

    @NotBlank
    @Length(max = 400)
    private final String description;

    @Override
    public Author toAuthor() {
        return new Author(name, email, description);
    }
}
