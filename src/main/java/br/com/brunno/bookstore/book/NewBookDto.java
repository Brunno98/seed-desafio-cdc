package br.com.brunno.bookstore.book;

import br.com.brunno.bookstore.author.AuthorRepository;
import br.com.brunno.bookstore.category.CategoryRepository;

public interface NewBookDto {

    Book toBook(AuthorRepository authorRepository, CategoryRepository categoryRepository);

}
