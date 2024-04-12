package br.com.brunno.bookstore.paymentFlow;

import br.com.brunno.bookstore.author.Author;
import br.com.brunno.bookstore.book.Book;
import br.com.brunno.bookstore.book.BookRepository;
import br.com.brunno.bookstore.category.Category;
import br.com.brunno.bookstore.paymentflow.Order;
import br.com.brunno.bookstore.paymentflow.OrderItem;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class OrderTest {

    EntityManager entityManager = Mockito.mock(EntityManager.class);
    BookRepository bookRepository = mock(BookRepository.class);

    Author author = new Author("some name", "some.email@email.com", "some descriptin");
    Category category = new Category("some category");
    Book book = new Book("some title", "some digest", "some summary", BigDecimal.valueOf(100), 200, "some isbn", LocalDate.now().plusDays(1), category, author);
    Book otherBook = new Book("another title", "another digest", "another summary", BigDecimal.valueOf(150), 230, "another isbn", LocalDate.now().plusDays(1), category, author);
    OrderItem orderItem = new OrderItem();
    OrderItem otherOrderItem = new OrderItem();
    Order order = new Order();

    @ParameterizedTest
    @CsvSource({"1,100", "2,200"})
    @DisplayName("Soma total do pedido com 1 item deve ser igual ao valor total informado no pedido")
    void test(final int quantity, final BigDecimal total) {
        ReflectionTestUtils.setField(orderItem, "bookId", 1);
        ReflectionTestUtils.setField(orderItem, "quantity", quantity);
        ReflectionTestUtils.setField(order, "items", List.of(orderItem));
        ReflectionTestUtils.setField(order, "total", total);
        doReturn(Optional.of(book)).when(bookRepository).findById(1L);

        BigDecimal totalFromItems = order.calculateTotalFromItems(bookRepository);

        Assertions.assertThat(totalFromItems).isEqualTo(order.getTotal());
    }

    @Test
    @DisplayName("Soma total do pedido com mais de 1 item deve ser igual ao valor total informado no pedido")
    void test2() {
        ReflectionTestUtils.setField(orderItem, "bookId", 1);
        ReflectionTestUtils.setField(orderItem, "quantity", 2);
        ReflectionTestUtils.setField(otherOrderItem, "bookId", 2);
        ReflectionTestUtils.setField(otherOrderItem, "quantity", 2);
        ReflectionTestUtils.setField(order, "items", List.of(orderItem, otherOrderItem));
        ReflectionTestUtils.setField(order, "total", BigDecimal.valueOf(500));
        doReturn(Optional.of(book)).when(bookRepository).findById(1L);
        doReturn(Optional.of(otherBook)).when(bookRepository).findById(2L);

        BigDecimal totalFromItems = order.calculateTotalFromItems(bookRepository);

        Assertions.assertThat(totalFromItems).isEqualTo(order.getTotal());
    }

    @Test
    @DisplayName("Caso algum dos ids passados não existir, então lanca exceção")
    void bookNotFound() {
        ReflectionTestUtils.setField(orderItem, "bookId", 1);
        ReflectionTestUtils.setField(orderItem, "quantity", 2);
        ReflectionTestUtils.setField(otherOrderItem, "bookId", 2);
        ReflectionTestUtils.setField(otherOrderItem, "quantity", 2);
        ReflectionTestUtils.setField(order, "items", List.of(orderItem, otherOrderItem));
        ReflectionTestUtils.setField(order, "total", BigDecimal.valueOf(500));
        doReturn(Optional.of(book)).when(bookRepository).findById(1L);
        doReturn(Optional.empty()).when(bookRepository).findById(2L);

        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> order.calculateTotalFromItems(bookRepository));
    }
}
