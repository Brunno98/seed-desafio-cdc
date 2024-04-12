package br.com.brunno.bookstore.paymentflow.usecasevalidator;

import br.com.brunno.bookstore.author.Author;
import br.com.brunno.bookstore.book.Book;
import br.com.brunno.bookstore.book.BookRepository;
import br.com.brunno.bookstore.category.Category;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import br.com.brunno.bookstore.paymentflow.Order;
import br.com.brunno.bookstore.paymentflow.OrderItem;
import br.com.brunno.bookstore.paymentflow.webvalidator.TotalMatchCartItemsValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;

class TotalMatchCartItemsValidatorUseCaseTest {

    private final Author author = new Author("some author", "some.email@email.com", "some description");
    private final Category category = new Category("some category");

    BookRepository bookRepository = Mockito.mock(BookRepository.class);

    TotalMatchCartItemsValidatorUseCase totalMatchCartItemsValidator = new TotalMatchCartItemsValidatorUseCase(bookRepository);

    @DisplayName("o preço total deve estar de acordo com o valor do pedido")
    @ParameterizedTest
    @CsvSource({"62.97,false","99.99,true"})
    void test2(Double totalPrice, Boolean expected) {
        Book book = new Book("some title", "some digest", "some summary", BigDecimal.valueOf(24.99), 200, "some isbn", LocalDate.now().plusDays(1), category, author);
        Book book2 = new Book("some title2", "some digest2", "some summary2", BigDecimal.valueOf(12.99), 130, "some isbn2", LocalDate.now().plusDays(1), category, author);
        Mockito.doReturn(Optional.of(book)).when(bookRepository).findById(1L);
        Mockito.doReturn(Optional.of(book2)).when(bookRepository).findById(2L);
        OrderItem orderItem1 = createOrderItem(1, 2);
        OrderItem orderItem2 = createOrderItem(2, 1);
        Order newOrder = createOrder(totalPrice, orderItem1, orderItem2);
        NewPurchaseRequest request = new NewPurchaseRequest();
        ReflectionTestUtils.setField(request, "order", newOrder);
        AtomicBoolean invalid = new AtomicBoolean(false);

        totalMatchCartItemsValidator.validate(request, () -> invalid.set(true));

        Assertions.assertThat(invalid.get()).isEqualTo(expected);
    }

    private OrderItem createOrderItem(Integer bookId, Integer quantity) {
        OrderItem orderItem = new OrderItem();
        ReflectionTestUtils.setField(orderItem, "bookId", bookId);
        ReflectionTestUtils.setField(orderItem, "quantity", quantity);
        return orderItem;
    }

    private Order createOrder(Double total, OrderItem... orderItems) {
        Order newOrder = new Order();
        ReflectionTestUtils.setField(newOrder, "total", BigDecimal.valueOf(total));
        ReflectionTestUtils.setField(newOrder, "items", Arrays.stream(orderItems).collect(Collectors.toList()));
        return newOrder;
    }

}