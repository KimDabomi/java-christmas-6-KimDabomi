package christmas.domain.exception;

public class OrderItemException extends IllegalArgumentException {
    private static final String numberType = "[+-]?\\d*(\\.\\d+)?";

    public OrderItemException(String errorMessage) {
        super(errorMessage);
    }

    public static void checkQuantityType(String quantity) {
        if (!quantity.matches(numberType)) {
            throw new OrderItemException(ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE.getErrorMessage());
        }
    }
}
