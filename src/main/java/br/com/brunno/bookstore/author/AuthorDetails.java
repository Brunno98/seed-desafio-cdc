package br.com.brunno.bookstore.author;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class AuthorDetails {
    private Long id;
    private String name;
    private String email;
    private String description;
    private LocalDateTime registrationInstant;

    public static AuthorDetails from(Author author) {
        return new AuthorDetails(
                author.getId(),
                author.getName(),
                author.getEmail(),
                author.getDescription(),
                author.getRegistrationInstant()
        );
    }
}
