package christmas.domain.order;

import christmas.domain.menu.Menu;

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

    public int getTotalAmountBeforeDiscount() {
        int totalAmountBeforeDiscount = 0;

        for (OrderItem orderItem : this.orderItems) {
            totalAmountBeforeDiscount += orderItem.getQuantity() * orderItem.getMenu().getPrice();
        }

        return totalAmountBeforeDiscount;
    }

    public int getTotalQuantityForCategory(String category) {
        return orderItems.stream()
                .filter(item -> item.getMenu().getCategory().equals(category))
                .mapToInt(OrderItem::getQuantity)
                .sum();
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

    public void printOrderList() {
        StringBuilder orderList = new StringBuilder();

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem item = orderItems.get(i);
            orderList.append(item.getMenu().getFoodName()).append(" ").append(item.getQuantity()).append("개\n");
        }

        System.out.println(orderList.toString());
    }
}
