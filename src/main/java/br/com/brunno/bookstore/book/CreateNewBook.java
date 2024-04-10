package br.com.brunno.bookstore.book;

import br.com.brunno.bookstore.author.AuthorRepository;
import br.com.brunno.bookstore.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateNewBook {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public Book create(NewBookRequest newBookRequest) {
        Book book = newBookRequest.toBook(authorRepository, categoryRepository);
        return bookRepository.save(book);
    }
}
