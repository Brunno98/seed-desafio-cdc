package br.com.brunno.bookstore.book;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class CreateBookController {

    private final CreateNewBook createNewBook;

    @PostMapping
    public NewBookResponse createBook(@RequestBody @Valid NewBookRequest newBookRequest) {
        Book book = createNewBook.create(newBookRequest);

        return NewBookResponse.from(book);
    }
}
