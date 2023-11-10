package christmas.domain.order;

import christmas.domain.event.Menu;

import christmas.domain.exception.OrderException;
import java.util.List;

public class Order {
    private final List<OrderItem> orderItems;

    public Order(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public static int getDate(String date) {
        OrderException.checkDateType(date);

        int visitingDate = Integer.parseInt(date);
        OrderException.checkDateRange(visitingDate);

        return visitingDate;
    }

    public void printOrderItems() {
        StringBuilder orderList = new StringBuilder();
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem item = orderItems.get(i);
            orderList.append(item.getMenu().getFoodName()).append("-").append(item.getQuantity());
            if (i < orderItems.size() - 1) {
                orderList.append(",");
            }
        }
        System.out.println(orderList.toString());
    }

    public static Order getOrderItems(String[] menuItems, List<OrderItem> orderItemList) {
        for (String item : menuItems) {
            String[] details = item.split("-");
            OrderException.checkOrderType(menuItems);

            String menuName = details[0].trim();
            String quantity = details[1].trim();

            Menu menu = Menu.of(Menu.removeWhiteSpace(menuName));

            orderItemList.add(new OrderItem(menu, quantity));
        }

        OrderException.checkOrderItemExceptions(orderItemList);
        return new Order(orderItemList);
    }

    public static int getTotalAmountBeforeDiscount(List<OrderItem> orderItemsList) {
        int totalAmountBeforeDiscount = 0;

        for (OrderItem orderItem : orderItemsList) {
            totalAmountBeforeDiscount += orderItem.getQuantity() * orderItem.getMenu().getPrice();
        }

        OrderException.checkMinimumTotalAmount(totalAmountBeforeDiscount);
        return totalAmountBeforeDiscount;
    }
}
