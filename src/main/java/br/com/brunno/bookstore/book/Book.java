package br.com.brunno.bookstore.book;

import br.com.brunno.bookstore.author.Author;
import br.com.brunno.bookstore.category.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String digest;

    private String summary;

    private BigDecimal price;

    private int numberOfPages;

    private String isbn;

    private LocalDate publishDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @Deprecated
    public Book() {}

    public Book(
            String title,
            String digest,
            String summary,
            BigDecimal price,
            int numberOfPages,
            String isbn,
            LocalDate publishDate,
            Category category,
            Author author
    ) {
        this.title = title;
        this.digest = digest;
        this.summary = summary;
        this.price = price;
        this.numberOfPages = numberOfPages;
        this.isbn = isbn;
        this.publishDate = publishDate;
        this.category = category;
        this.author = author;
    }
}
