package christmas.domain.order;

import christmas.domain.menu.Menu;
import christmas.domain.exception.OrderItemException;

public class OrderItem {
    private final Menu menu;
    private final String quantity;

    public OrderItem(Menu menu, String quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getQuantity() {
        OrderItemException.checkQuantityType(quantity);
        return Integer.parseInt(quantity);
    }
}
