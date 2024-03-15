package br.com.brunno.bookstore.paymentFlow.validator;

import br.com.brunno.bookstore.author.Author;
import br.com.brunno.bookstore.book.Book;
import br.com.brunno.bookstore.category.Category;
import br.com.brunno.bookstore.paymentflow.NewPurchaseRequest;
import br.com.brunno.bookstore.paymentflow.Order;
import br.com.brunno.bookstore.paymentflow.OrderItem;
import br.com.brunno.bookstore.paymentflow.validator.TotalMatchCartItemsValidator;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TotalMatchCartItemsValidatorTest {

    private final Author author = new Author("some author", "some.email@email.com", "some description");
    private final Category category = new Category("some category");

    EntityManager entityManager = Mockito.mock(EntityManager.class);

    TotalMatchCartItemsValidator totalMatchCartItemsValidator = new TotalMatchCartItemsValidator(entityManager);

    @DisplayName("o preço total deve estar de acordo com o valor do pedido")
    @ParameterizedTest
    @CsvSource({"62.97,false","99.99,true"})
    void test2(Double totalPrice, Boolean expected) {
        Book book = new Book("some title", "some digest", "some summary", BigDecimal.valueOf(24.99), 200, "some isbn", LocalDate.now().plusDays(1), category, author);
        Book book2 = new Book("some title2", "some digest2", "some summary2", BigDecimal.valueOf(12.99), 130, "some isbn2", LocalDate.now().plusDays(1), category, author);
        Mockito.doReturn(book).when(entityManager).find(Book.class, 1);
        Mockito.doReturn(book2).when(entityManager).find(Book.class, 2);
        OrderItem orderItem1 = createOrderItem(1, 2);
        OrderItem orderItem2 = createOrderItem(2, 1);
        Order newOrder = createOrder(totalPrice, orderItem1, orderItem2);
        NewPurchaseRequest request = new NewPurchaseRequest();
        ReflectionTestUtils.setField(request, "order", newOrder);
        Errors errors = new BeanPropertyBindingResult(request, "test");

        totalMatchCartItemsValidator.validate(request, errors);

        Assertions.assertThat(errors.hasErrors()).isEqualTo(expected);
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
