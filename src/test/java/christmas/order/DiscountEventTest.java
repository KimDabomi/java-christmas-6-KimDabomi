package christmas.order;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.event.DiscountEvent;
import christmas.domain.menu.Menu;
import christmas.domain.order.Order;
import christmas.domain.order.OrderItem;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DiscountEventTest {
    private static Stream<Arguments> provideDiscountTypeTestData() {
        return Stream.of(
            Arguments.of(DiscountEvent.CHRISTMAS_D_DAY, LocalDate.of(2023, 12, 4), new Order(List.of(new OrderItem(Menu.TAPAS, "1"))), 1200),
            Arguments.of(DiscountEvent.WEEKDAY_DISCOUNT, LocalDate.of(2023, 12, 4), new Order(List.of(new OrderItem(Menu.TAPAS, "1"), new OrderItem(Menu.ICE_CREAM, "1"))), 2023),
            Arguments.of(DiscountEvent.WEEKEND_DISCOUNT, LocalDate.of(2023, 12, 2), new Order(List.of(new OrderItem(Menu.T_BONE_STEAK, "1"), new OrderItem(Menu.ICE_CREAM, "1"))), 2023),
            Arguments.of(DiscountEvent.SPECIAL_DISCOUNT, LocalDate.of(2023, 12, 4), new Order(List.of(new OrderItem(Menu.T_BONE_STEAK, "1"), new OrderItem(Menu.ICE_CREAM, "1"))), 1000),
            Arguments.of(DiscountEvent.GIFT_CHAMPAGNE, LocalDate.of(2023, 12, 4), new Order(List.of(new OrderItem(Menu.T_BONE_STEAK, "4"), new OrderItem(Menu.ICE_CREAM, "1"))), 25000)
        );
    }

    private static Stream<Arguments> provideTotalDiscountAmountTestData() {
        return Stream.of(
            Arguments.of(LocalDate.of(2023, 12, 4), new Order(List.of(new OrderItem(Menu.T_BONE_STEAK, "1"), new OrderItem(Menu.BBQ_RIBS, "1"), new OrderItem(Menu.CHOCOLATE_CAKE, "2"), new OrderItem(Menu.ZERO_COLA, "1"))), 31246),
            Arguments.of(LocalDate.of(2023, 12, 27), new Order(List.of(new OrderItem(Menu.TAPAS, "1"), new OrderItem(Menu.ZERO_COLA, "1"))), 0)
        );
    }

    private static Stream<Arguments> provideTotalAmountTestData() {
        return Stream.of(
                Arguments.of(LocalDate.of(2023, 12, 4), new Order(List.of(new OrderItem(Menu.T_BONE_STEAK, "1"), new OrderItem(Menu.BBQ_RIBS, "1"), new OrderItem(Menu.CHOCOLATE_CAKE, "2"), new OrderItem(Menu.ZERO_COLA, "1"))), 135754),
                Arguments.of(LocalDate.of(2023, 12, 27), new Order(List.of(new OrderItem(Menu.TAPAS, "1"), new OrderItem(Menu.ZERO_COLA, "1"))), 8500)
        );
    }

    @ParameterizedTest(name = "{index} - 이벤트 유형: {0}, 예상 결과: {3}")
    @MethodSource("provideDiscountTypeTestData")
    @DisplayName("할인 이벤트별 할인 금액 계산")
    void testDiscountType(DiscountEvent event,  LocalDate testDate, Order testOrder, int expectedDiscount) {
        int discount = event.calculateDiscount(testOrder, testDate);
        assertThat(discount).isEqualTo(expectedDiscount);
    }

    @ParameterizedTest(name = "{index} - 총 할인 금액 : {2}")
    @MethodSource("provideTotalDiscountAmountTestData")
    @DisplayName("총 할인 금액 계산")
    void testTotalDiscountAmount(LocalDate testDate, Order testOrder, int expectedDiscount) {
        int discount = DiscountEvent.getTotalDiscountAmount(testOrder, testDate);
        assertThat(discount).isEqualTo(expectedDiscount);
    }

    @ParameterizedTest(name = "{index} - 할인 후 결제 금액 : {2}")
    @MethodSource("provideTotalAmountTestData")
    @DisplayName("할인 후 결제 예상 금액 계산")
    void testTotalAmount(LocalDate testDate, Order testOrder, int expectedDiscount) {
        int discount = DiscountEvent.getTotalAmount(testOrder, testDate);
        assertThat(discount).isEqualTo(expectedDiscount);
    }
}
