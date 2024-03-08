package br.com.brunno.bookstore.book;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class BookListItem {
    private Long id;
    private String title;

    public static BookListItem from(Book book) {
        return new BookListItem(book.getId(), book.getTitle());
    }

    public static List<BookListItem> listFrom(List<Book> books) {
        return books.stream()
                .map(BookListItem::from)
                .collect(Collectors.toList());
    }
}
