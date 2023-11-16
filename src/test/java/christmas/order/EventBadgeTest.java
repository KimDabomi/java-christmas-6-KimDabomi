package christmas.order;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.event.DiscountEvent;
import christmas.domain.event.EventBadge;
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

public class EventBadgeTest {
    private static Stream<Arguments> provideBadgeTestData() {
        return Stream.of(
                Arguments.of(LocalDate.of(2023, 12, 4), new Order(List.of(new OrderItem(Menu.T_BONE_STEAK, "1"), new OrderItem(Menu.BBQ_RIBS, "1"), new OrderItem(Menu.CHOCOLATE_CAKE, "2"), new OrderItem(Menu.ZERO_COLA, "1"))), "산타"),
                Arguments.of(LocalDate.of(2023, 12, 27), new Order(List.of(new OrderItem(Menu.TAPAS, "1"), new OrderItem(Menu.CHOCOLATE_CAKE, "5"), new OrderItem(Menu.ZERO_COLA, "1"))), "트리"),
                Arguments.of(LocalDate.of(2023, 12, 27), new Order(List.of(new OrderItem(Menu.TAPAS, "1"), new OrderItem(Menu.CHOCOLATE_CAKE, "4"), new OrderItem(Menu.ZERO_COLA, "1"))), "별"),
                Arguments.of(LocalDate.of(2023, 12, 27), new Order(List.of(new OrderItem(Menu.TAPAS, "1"), new OrderItem(Menu.ZERO_COLA, "1"))), "없음")
        );
    }

    @ParameterizedTest(name = "{index} - 배지 유형: {2}")
    @MethodSource("provideBadgeTestData")
    @DisplayName("이벤트 배지 유형별 확인")
    void testBadge(LocalDate testDate, Order testOrder, String expectedBadge) {
        int totalDiscountAmount = DiscountEvent.getTotalDiscountAmount(testOrder, testDate);
        String badge = EventBadge.getBadgeForDiscount(totalDiscountAmount);

        assertThat(badge).isEqualTo(expectedBadge);
    }
}
