package christmas.domain.order;

import java.util.List;

public class Order {
    private final List<OrderItem> orderItems;

    public Order(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
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
