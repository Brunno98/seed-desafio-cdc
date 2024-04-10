package br.com.brunno.bookstore.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class ListBooksController {

    private final BookFinder bookFinder;

    @GetMapping
    public List<BookListItem> listBook() {
        List<Book> bookList = bookFinder.findAll();

        return BookListItem.listFrom(bookList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDetails> getBookDetails(@PathVariable Long id) {
        Optional<Book> optionalBook = bookFinder.findById(id);
        if (optionalBook.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Book book = optionalBook.get();

        return ResponseEntity.ok(BookDetails.from(book));
    }

}
