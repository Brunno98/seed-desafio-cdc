package br.com.brunno.bookstore.author;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class CreateAuthorRequest {

    @NotBlank
    private final String name;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Length(max = 400)
    private final String description;

    @NotBlank
    private final String registrationInstant;

    public Author toDomain() {
        Author author = new Author();
        author.setName(name);
        author.setEmail(email);
        author.setDescription(description);

        LocalDateTime parsedRegitrationInstant =
                LocalDateTime.parse(registrationInstant, DateTimeFormatter.ISO_DATE_TIME);

        author.setRegistrationInstant(parsedRegitrationInstant);

        return author;
    }
}
