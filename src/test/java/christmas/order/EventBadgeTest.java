package christmas.order;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.event.DiscountEvent;
import christmas.domain.event.EventBadge;
import christmas.domain.menu.Menu;
import christmas.domain.order.Order;
import christmas.domain.order.OrderItem;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EventBadgeTest {
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Test
    @DisplayName("이벤트 배지 - 산타")
    void getBadgeForDiscount_배지_산타인_경우_동작확인() {
        orderItems.add(new OrderItem(Menu.T_BONE_STEAK, "1"));
        orderItems.add(new OrderItem(Menu.BBQ_RIBS, "1"));
        orderItems.add(new OrderItem(Menu.CHOCOLATE_CAKE, "2"));
        orderItems.add(new OrderItem(Menu.ZERO_COLA, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 4); // 실제 3일
        int totalDiscountAmount = DiscountEvent.getTotalDiscountAmount(testOrder, testDate);
        String badge = EventBadge.getBadgeForDiscount(totalDiscountAmount);
        assertThat(badge).isEqualTo("산타");
    }

    @Test
    @DisplayName("이벤트 배지 - 트리")
    void getBadgeForDiscount_배지_트리인_경우_동작확인() {
        orderItems.add(new OrderItem(Menu.TAPAS, "1"));
        orderItems.add(new OrderItem(Menu.CHOCOLATE_CAKE, "5"));
        orderItems.add(new OrderItem(Menu.ZERO_COLA, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 27); // 실제 26일
        int totalDiscountAmount = DiscountEvent.getTotalDiscountAmount(testOrder, testDate);
        String badge = EventBadge.getBadgeForDiscount(totalDiscountAmount);
        assertThat(badge).isEqualTo("트리");
    }

    @Test
    @DisplayName("이벤트 배지 - 별")
    void getBadgeForDiscount_배지_별인_경우_동작확인() {
        orderItems.add(new OrderItem(Menu.TAPAS, "1"));
        orderItems.add(new OrderItem(Menu.CHOCOLATE_CAKE, "4"));
        orderItems.add(new OrderItem(Menu.ZERO_COLA, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 27); // 실제 26일
        int totalDiscountAmount = DiscountEvent.getTotalDiscountAmount(testOrder, testDate);
        String badge = EventBadge.getBadgeForDiscount(totalDiscountAmount);
        assertThat(badge).isEqualTo("별");
    }

    @Test
    @DisplayName("이벤트 배지 - 없음")
    void getBadgeForDiscount_배지_없는_경우_동작확인() {
        orderItems.add(new OrderItem(Menu.TAPAS, "1"));
        orderItems.add(new OrderItem(Menu.ZERO_COLA, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 27); // 실제 26일
        int totalDiscountAmount = DiscountEvent.getTotalDiscountAmount(testOrder, testDate);
        String badge = EventBadge.getBadgeForDiscount(totalDiscountAmount);
        assertThat(badge).isEqualTo("없음");
    }
}
