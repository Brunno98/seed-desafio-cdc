package br.com.brunno.bookstore.book;

import br.com.brunno.bookstore.author.AuthorDetails;
import br.com.brunno.bookstore.category.CategoryDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class BookDetails {

    private Long id;
    private String title;
    private String digest;
    private String summary;
    private double price;
    private int numberOfPages;
    private String isbn;
    private LocalDate publishDate;
    private CategoryDetails category;
    private AuthorDetails author;

    public static BookDetails from(Book book) {
        return new BookDetails(
                book.getId(),
                book.getTitle(),
                book.getDigest(),
                book.getSummary(),
                book.getPrice().doubleValue(),
                book.getNumberOfPages(),
                book.getIsbn(),
                book.getPublishDate(),
                CategoryDetails.from(book.getCategory()),
                AuthorDetails.from(book.getAuthor())
        );
    }
}
