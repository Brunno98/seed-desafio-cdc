package br.com.brunno.bookstore.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class NewBookResponse {
    private Long id;
    private String title;
    private String digest;
    private String summary;
    private double price;
    private int numberOfPages;
    private String isbn;
    private LocalDate publishDate;
    private Long categoryId;
    private Long authorid;

    public static NewBookResponse from(Book book) {
        return new NewBookResponse(
                book.getId(),
                book.getTitle(),
                book.getDigest(),
                book.getSummary(),
                book.getPrice().doubleValue(),
                book.getNumberOfPages(),
                book.getIsbn(),
                book.getPublishDate(),
                book.getCategory().getId(),
                book.getAuthor().getId()
        );
    }
}
