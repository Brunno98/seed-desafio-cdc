package br.com.brunno.bookstore.author;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CreateNewAuthor {

    private final AuthorRepository authorRepository;

    @Autowired
    public CreateNewAuthor(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author create(@Valid NewAuthorData newAuthorData) {
        Author author = newAuthorData.toAuthor();
        authorRepository.save(author);
        return author;
    }

}
