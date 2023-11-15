package christmas.order;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.menu.Menu;
import christmas.domain.order.Order;
import christmas.domain.order.OrderInformation;
import christmas.domain.order.OrderItem;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderTest {
    @Test
    @DisplayName("날짜 확인")
    void getDate_확인() {
        String date = "3";
        int resultDate = OrderInformation.getDate(date);
        assertThat(resultDate).isEqualTo(3);
    }

    @Test
    @DisplayName("할인 전 총 금액 계산")
    void getTotalPriceBeforeDiscount_할인_전_총_금액_확인() {
        OrderItem orderItem = new OrderItem(Menu.T_BONE_STEAK, "4");
        List<OrderItem> orderItemsList = new ArrayList<OrderItem>();
        Order order = new Order(orderItemsList);
        orderItemsList.add(orderItem);
        int totalPrice = order.getTotalAmountBeforeDiscount();

        assertThat(totalPrice).isEqualTo(220000);
    }
}
