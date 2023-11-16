package christmas.domain.exception;

import christmas.domain.order.OrderItem;
import java.util.List;

public class OrderException extends IllegalArgumentException {
    private static final String numberType = "[+-]?\\d*(\\.\\d+)?";

    public OrderException(String errorMessage) {
        super(errorMessage);
    }

    public static void checkDateType(String date) {
        if (!date.matches(numberType)) {
            throw new OrderException(ErrorMessage.DATE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    public static void checkDateRange(int date) {
        if (date < NumberOfException.MIN_DATE.getNumber() || date > NumberOfException.MAX_DATE.getNumber()) {
            throw new OrderException(ErrorMessage.DATE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    public static void checkOrderType(String[] menuItems) {
        for (String menuItem : menuItems) {
            String[] details = menuItem.split("-");

            if (details.length != NumberOfException.ORDER_LIST_SIZE.getNumber()) {
                throw new OrderException(ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE.getErrorMessage());
            }
        }
    }

    public static void checkOrderItemExceptions(List<OrderItem> orderItemList) {
        checkOnlyDrink(orderItemList);
        checkTotalQuantity(orderItemList);
        checkQuantityRange(orderItemList);
        checkDuplicationMenu(orderItemList);
    }

    private static void checkOnlyDrink(List<OrderItem> orderItemList) {
        boolean allDrinks = true;

        for (OrderItem orderItem : orderItemList) {
            if (!orderItem.getMenu().getCategory().equals("음료")) {
                allDrinks = false;
                break;
            }
        }

        if (allDrinks) {
            throw new OrderException(ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE.getErrorMessage());
        }
    }

    private static void checkTotalQuantity(List<OrderItem> orderItemList) {
        int totalQuantity = 0;

        for (OrderItem orderItem : orderItemList) {
            totalQuantity += orderItem.getQuantity();
        }

        if (totalQuantity > NumberOfException.MAX_QUANTITY.getNumber()) {
            throw new OrderException(ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE.getErrorMessage());
        }
    }

    private static void checkQuantityRange(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getQuantity() < NumberOfException.MIN_QUANTITY.getNumber()) {
                throw new OrderException(ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE.getErrorMessage());
            }
        }
    }

    private static void checkDuplicationMenu(List<OrderItem> orderItemList) {
        for (int i = 0; i < orderItemList.size() - 1; i++) {
            if (orderItemList.get(i).getMenu().getFoodName().equals(orderItemList.get(i + 1).getMenu().getFoodName())) {
                throw new OrderException(ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE.getErrorMessage());
            }
        }
    }
}
