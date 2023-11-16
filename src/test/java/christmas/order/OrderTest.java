package christmas.order;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.menu.Menu;
import christmas.domain.order.Order;
import christmas.domain.order.OrderItem;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderTest {
    private static Stream<Arguments> provideOrderQuantityTestData() {
        return Stream.of(
            Arguments.of(new OrderItem(Menu.T_BONE_STEAK, "4"), 4)
        );
    }

    private static Stream<Arguments> provideOrderAmountTestData() {
        return Stream.of(
                Arguments.of(new OrderItem(Menu.T_BONE_STEAK, "4"), 220000)
        );
    }

    @ParameterizedTest(name = "{index} - 주문 내역: {0}, 예상 결과: {1}")
    @MethodSource("provideOrderQuantityTestData")
    @DisplayName("카테고리 개수 확인")
    void testTotalQuantityForCategory(OrderItem orderItem, int expected) {
        List<OrderItem> orderItemsList = new ArrayList<OrderItem>();
        Order order = new Order(orderItemsList);
        orderItemsList.add(orderItem);
        int categoryQuantity = order.getTotalQuantityForCategory("메인");

        assertThat(categoryQuantity).isEqualTo(expected);
    }

    @ParameterizedTest(name = "{index} - 주문 내역: {0}, 예상 결과: {1}")
    @MethodSource("provideOrderAmountTestData")
    @DisplayName("할인 전 총 금액 계산 확인")
    void testTotalPriceBeforeDiscount(OrderItem orderItem, int expected) {
        List<OrderItem> orderItemsList = new ArrayList<OrderItem>();
        Order order = new Order(orderItemsList);
        orderItemsList.add(orderItem);
        int totalPrice = order.getTotalAmountBeforeDiscount();

        assertThat(totalPrice).isEqualTo(expected);
    }
}
