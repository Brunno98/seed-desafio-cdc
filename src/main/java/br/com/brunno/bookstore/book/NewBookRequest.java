package br.com.brunno.bookstore.book;

import br.com.brunno.bookstore.author.Author;
import br.com.brunno.bookstore.author.AuthorRepository;
import br.com.brunno.bookstore.category.Category;
import br.com.brunno.bookstore.category.CategoryRepository;
import br.com.brunno.bookstore.shared.validator.IdExists;
import br.com.brunno.bookstore.shared.validator.UniqueValue;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

@Getter
public class NewBookRequest implements NewBookDto {

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

    @Override
    public Book toBook(AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Assert.isTrue(optionalAuthor.isPresent(), "Should exists author with id "+authorId+" when convert request to book");
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        Assert.isTrue(optionalCategory.isPresent(), "Should exists category with id "+categoryId+" when convert request to book");
        return new Book(
                title,
                digest,
                summary,
                BigDecimal.valueOf(price),
                numberOfPages,
                isbn,
                publishDate,
                optionalCategory.get(),
                optionalAuthor.get()
        );
    }

}
