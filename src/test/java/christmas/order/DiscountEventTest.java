package christmas.order;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.event.DiscountEvent;
import christmas.domain.menu.Menu;
import christmas.domain.order.Order;
import christmas.domain.order.OrderItem;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class DiscountEventTest {
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Test
    @DisplayName("크리스마스 디데이 할인 금액 계산")
    void CHRISTMAS_D_DAY_동작확인() {
        orderItems.add(new OrderItem(Menu.TAPAS, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 4); // 실제 3일
        int discount = DiscountEvent.CHRISTMAS_D_DAY.calculateDiscount(testOrder, testDate);
        assertThat(discount).isEqualTo(1200);
    }

    @Test
    @DisplayName("평일 할인 금액 계산")
    void WEEKDAY_DISCOUNT_동작확인() {
        orderItems.add(new OrderItem(Menu.TAPAS, "1"));
        orderItems.add(new OrderItem(Menu.ICE_CREAM, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 4); // 실제 3일
        int discount = DiscountEvent.WEEKDAY_DISCOUNT.calculateDiscount(testOrder, testDate);
        assertThat(discount).isEqualTo(2023);
    }

    @Test
    @DisplayName("주말 할인 금액 계산")
    void WEEKEND_DISCOUNTT_동작확인() {
        orderItems.add(new OrderItem(Menu.T_BONE_STEAK, "1"));
        orderItems.add(new OrderItem(Menu.ICE_CREAM, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 2); // 실제 1일
        int discount = DiscountEvent.WEEKEND_DISCOUNT.calculateDiscount(testOrder, testDate);
        assertThat(discount).isEqualTo(2023);
    }

    @Test
    @DisplayName("특별 할인 금액 계산")
    void SPECIAL_DISCOUNT_동작확인() {
        orderItems.add(new OrderItem(Menu.T_BONE_STEAK, "1"));
        orderItems.add(new OrderItem(Menu.ICE_CREAM, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 4); // 실제 3일
        int discount = DiscountEvent.SPECIAL_DISCOUNT.calculateDiscount(testOrder, testDate);
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("증정 이벤트 금액 계산")
    void GIFT_CHAMPAGNE_동작확인() {
        orderItems.add(new OrderItem(Menu.T_BONE_STEAK, "4"));
        orderItems.add(new OrderItem(Menu.ICE_CREAM, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 4); // 실제 3일
        int discount = DiscountEvent.GIFT_CHAMPAGNE.calculateDiscount(testOrder, testDate);
        assertThat(discount).isEqualTo(25000);
    }

    @Test
    @DisplayName("총 할인 금액 계산 - 총혜택 금액 0원이 아닌 경우")
    void getTotalDiscountAmount_10000원_이상_주문인_경우_동작확인() {
        orderItems.add(new OrderItem(Menu.T_BONE_STEAK, "1"));
        orderItems.add(new OrderItem(Menu.BBQ_RIBS, "1"));
        orderItems.add(new OrderItem(Menu.CHOCOLATE_CAKE, "2"));
        orderItems.add(new OrderItem(Menu.ZERO_COLA, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 4); // 실제 3일
        int discount = DiscountEvent.getTotalDiscountAmount(testOrder, testDate);
        assertThat(discount).isEqualTo(31246);
    }

    @Test
    @DisplayName("총 할인 금액 계산 - 총혜택 금액 0원인 경우")
    void getTotalDiscountAmount_10000원_미만_주문인_경우_동작확인() {
        orderItems.add(new OrderItem(Menu.TAPAS, "1"));
        orderItems.add(new OrderItem(Menu.ZERO_COLA, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 27); // 실제 26일
        int discount = DiscountEvent.getTotalDiscountAmount(testOrder, testDate);
        assertThat(discount).isEqualTo(0);
    }

    @Test
    @DisplayName("할인 후 예상 결제 금액 - 10000원 이상 주문인 경우")
    void getTotalDiscountAmount_10000원_이상_주문인_경우_최종결제금액_동작확인() {
        orderItems.add(new OrderItem(Menu.T_BONE_STEAK, "1"));
        orderItems.add(new OrderItem(Menu.BBQ_RIBS, "1"));
        orderItems.add(new OrderItem(Menu.CHOCOLATE_CAKE, "2"));
        orderItems.add(new OrderItem(Menu.ZERO_COLA, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 4); // 실제 3일
        int discount = DiscountEvent.getTotalAmount(testOrder, testDate);
        assertThat(discount).isEqualTo(135754);
    }

    @Test
    @DisplayName("총 할인 금액 계산 - 10000원 미만 주문인 경우")
    void getTotalDiscountAmount_10000원_미만_주문인_경우_최종결제금액_동작확인() {
        orderItems.add(new OrderItem(Menu.TAPAS, "1"));
        orderItems.add(new OrderItem(Menu.ZERO_COLA, "1"));
        Order testOrder = new Order(orderItems);

        LocalDate testDate = LocalDate.of(2023, 12, 27); // 실제 26일
        int discount = DiscountEvent.getTotalAmount(testOrder, testDate);
        assertThat(discount).isEqualTo(8500);
    }
}
