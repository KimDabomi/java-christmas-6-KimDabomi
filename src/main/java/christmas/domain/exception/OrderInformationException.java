package christmas.domain.exception;

public class OrderInformationException extends IllegalArgumentException{
    private static final String numberType = "[+-]?\\d*(\\.\\d+)?";

    public OrderInformationException(String errorMessage) {
        super(errorMessage);
    }
    
    public static void checkDateType(String date) {
        if (!date.matches(numberType)) {
            throw new OrderInformationException(ErrorMessage.DATE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    public static void checkDateRange(int date) {
        if (date < NumberOfException.MIN_DATE.getNumber() || date > NumberOfException.MAX_DATE.getNumber()) {
            throw new OrderInformationException(ErrorMessage.DATE_ERROR_MESSAGE.getErrorMessage());
        }
    }

    public static void checkOrderType(String[] menuItems) {
        for (String menuItem : menuItems) {
            String[] details = menuItem.split("-");

            if (details.length != NumberOfException.ORDER_LIST_SIZE.getNumber()) {
                throw new OrderInformationException(ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE.getErrorMessage());
            }
        }
    }
}
