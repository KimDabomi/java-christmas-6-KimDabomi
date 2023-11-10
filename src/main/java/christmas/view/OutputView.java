package christmas.view;

import christmas.domain.order.Order;
import christmas.domain.order.OrderItem;
import java.util.List;

public class OutputView {
    private Order order;
    public OutputView() {
    }

    public void showOrderList(Order order) {
        if (order != null) {
            order.printOrderItems();
        }
    }

    public void showEventPreview(int date) {
        System.out.printf("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!", date);
        System.out.println();
    }
}
