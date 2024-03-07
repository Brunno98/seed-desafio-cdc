package br.com.brunno.bookstore.author;

public record CreateAuthorResponse(
        Long id,
        String name,
        String email,
        String description,
        String registrationInstant
) {
    public static CreateAuthorResponse from(Author author) {
        return new CreateAuthorResponse(
                author.getId(),
                author.getName(),
                author.getEmail(),
                author.getDescription(),
                author.getRegistrationInstant().toString()
        );
    }
}
