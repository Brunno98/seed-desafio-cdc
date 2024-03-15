package br.com.brunno.bookstore.book;

import br.com.brunno.bookstore.author.Author;
import br.com.brunno.bookstore.category.Category;
import br.com.brunno.bookstore.shared.validator.IdExists;
import br.com.brunno.bookstore.shared.validator.UniqueValue;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class NewBookRequest {

    @NotBlank
    @UniqueValue(domainClass = Book.class, fieldName = "title")
    private String title;

    @NotBlank
    @Size(max = 500)
    private String digest;

    private String summary;

    @Min(20)
    @Digits(integer = 10, fraction = 2)
    private double price;

    @Min(100)
    private int numberOfPages;

    @NotBlank
    @UniqueValue(domainClass = Book.class, fieldName = "isbn")
    private String isbn;

    @Future
    private LocalDate publishDate;

    @NotNull
    @IdExists(domain = Category.class)
    private Long categoryId;

    @NotNull
    @IdExists(domain = Author.class)
    private Long authorId;

    public Book toDomain(EntityManager entityManager) {
        Author author = (Author) entityManager.createQuery("FROM Author a where a.id = :id").setParameter("id", authorId).getSingleResult();
        Category category = (Category) entityManager.createQuery("FROM Category c where c.id = :id").setParameter("id", categoryId).getSingleResult();
        return new Book(
                title,
                digest,
                summary,
                BigDecimal.valueOf(price),
                numberOfPages,
                isbn,
                publishDate,
                category,
                author
        );
    }
}
