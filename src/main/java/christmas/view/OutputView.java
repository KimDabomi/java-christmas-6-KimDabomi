package christmas.view;

import christmas.domain.order.Order;
import java.text.DecimalFormat;

public class OutputView {
    private Order order;
    public OutputView() {
    }

    public void showOrderItems(Order order) {
        if (order != null) {
            order.printOrderItems();
        }
    }

    public void showEventPreview(int date) {
        System.out.printf("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!", date);
        System.out.println();
    }

    public void showOrderList(Order order) {
        if (order != null) {
            System.out.println("\n<주문 메뉴>");
            order.printOrderList();
        }
    }

    public void showTotalAmountBeforeDiscount(Order order) {
        if (order != null) {
            System.out.println("\n<할인 전 총주문 금액>");
            DecimalFormat decFormat = new DecimalFormat("###,###");
            String totalAmountBeforeDiscount = decFormat.format(order.getTotalAmountBeforeDiscount());
            System.out.printf("%s원", totalAmountBeforeDiscount);
        }
    }
}
