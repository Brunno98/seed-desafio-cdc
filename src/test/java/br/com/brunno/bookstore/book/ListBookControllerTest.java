package br.com.brunno.bookstore.book;

import br.com.brunno.bookstore.author.Author;
import br.com.brunno.bookstore.category.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class ListBookControllerTest {

    BookRepository bookRepository = Mockito.mock(BookRepository.class);
    BookFinder bookFinder = new BookFinder(bookRepository);
    ListBooksController listBooksController = new ListBooksController(bookFinder);

    @Test
    @DisplayName("when get book details not found a book then should return bad request")
    void bookNotFound() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<BookDetails> response = listBooksController.getBookDetails(1L);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("when get book should return the book details")
    void getBook() {
        Author author = new Author("some author", "example@email.com", "some description");
        Category category = new Category("some category");
        Book book = new Book("some title", "some title", "some summary", BigDecimal.TEN, 200, "some isbn", LocalDate.now(), category, author);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<BookDetails> response = listBooksController.getBookDetails(1L);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
