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
            throw new OrderException(ErrorMessage.DATE_TYPE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    public static void checkDateRange(int date) {
        if (date < Restriction.MIN_DATE.getNumber() || date > Restriction.MAX_DATE.getNumber()) {
            throw new OrderException(ErrorMessage.DATE_RANGE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    public static void checkMinimumTotalAmount(int totalAmount) {
        if (totalAmount < Restriction.MIN_AMOUNT.getNumber()) {
            throw new OrderException(ErrorMessage.TOTAL_AMOUNT_MINIMUN_ERROR_MESSAGE.getErrorMessage());
        }
    }

    public static void checkOrderType(String[] menuItems) {
        for (String menuItem : menuItems) {
            String[] details = menuItem.split("-");
            if (details.length != Restriction.ORDER_LIST_SIZE.getNumber()) {
                throw new IllegalArgumentException(ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE.getErrorMessage());
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
            throw new OrderException(ErrorMessage.ORDER_ONLY_DRINK_ERROR_MESSAGE.getErrorMessage());
        }
    }

    private static void checkTotalQuantity(List<OrderItem> orderItemList) {
        int totalQuantity = 0;
        for (OrderItem orderItem : orderItemList) {
            totalQuantity += orderItem.getQuantity();
        }
        if (totalQuantity > Restriction.MAX_QUANTITY.getNumber()) {
            throw new OrderException(ErrorMessage.ORDER_TOTAL_QUANTITY_MAXIMUM_ERROR_MESSAGE.getErrorMessage());
        }
    }

    private static void checkQuantityRange(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList) {
            if (orderItem.getQuantity() < Restriction.MIN_QUANTITY.getNumber()) {
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
