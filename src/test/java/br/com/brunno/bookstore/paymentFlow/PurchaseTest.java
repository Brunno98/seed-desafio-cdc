package br.com.brunno.bookstore.paymentFlow;

import br.com.brunno.bookstore.country.Country;
import br.com.brunno.bookstore.coupon.Coupon;
import br.com.brunno.bookstore.paymentflow.Order;
import br.com.brunno.bookstore.paymentflow.Purchase;
import br.com.brunno.bookstore.state.State;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PurchaseTest {

    Country brazil = new Country("Brazil");
    State rioDeJaneiro = new State("Rio de Janeiro", brazil);
    Order order = new Order();
    Purchase purchase = new Purchase(
            "email@email.com",
            "some name",
            "some last name",
            "60738350001",
            "some address",
            "some adjunct",
            "some city",
            "some phone numer",
            "some CEP",
            brazil,
            order
    );
    Coupon coupon = new Coupon("test123", .5, LocalDate.now().plusDays(1));
    Coupon otherCoupon = new Coupon("other123", .2, LocalDate.now().plusDays(1));

    @Test
    @DisplayName("Quando compra tiver estado, dever ser possivel ler o nome do estado")
    void testState() {
        purchase.setState(rioDeJaneiro);

        String stateName = purchase.getStateName();

        Assertions.assertThat(stateName).isEqualTo("Rio de Janeiro");
    }

    @Test
    @DisplayName("Quando compra não tiver estado, então ler o nome do estado dever retornar null")
    void testNotState() {
        String stateName = purchase.getStateName();

        Assertions.assertThat(stateName).isNull();
    }

    @Test
    @DisplayName("Não deve ser possivel aplicar 2 cupons para uma compra")
    void doubleCuopon() {
        purchase.applyCoupon(coupon);

        Assertions
                .assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> purchase.applyCoupon(otherCoupon));
    }

    @Test
    @DisplayName("Quando compra não tiver cupom aplicado então valor original deve ser igual ao falor final")
    void testTotalValue() {
        ReflectionTestUtils.setField(order, "total", BigDecimal.valueOf(100));
        ReflectionTestUtils.setField(purchase, "order", order);

        Assertions.assertThat(purchase.getFinalValue()).isEqualTo(purchase.getOriginalValue());
    }

    @Test
    @DisplayName("Quando compra tiver cupom aplicado então o valor final deve ser menor que o valor original")
    void testDiscuntValue() {
        ReflectionTestUtils.setField(order, "total", BigDecimal.valueOf(100));
        ReflectionTestUtils.setField(purchase, "order", order);

        purchase.applyCoupon(coupon);

        Assertions.assertThat(purchase.getFinalValue()).isLessThan(purchase.getOriginalValue());
    }
}
