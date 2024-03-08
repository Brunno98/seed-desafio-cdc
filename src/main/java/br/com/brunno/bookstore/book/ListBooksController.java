package br.com.brunno.bookstore.book;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class ListBooksController {

    private final BookRepository bookRepository;

    @GetMapping
    public List<BookListItem> listBook() {
        List<Book> bookList = bookRepository.findAll();

        return BookListItem.listFrom(bookList);
    }

}
