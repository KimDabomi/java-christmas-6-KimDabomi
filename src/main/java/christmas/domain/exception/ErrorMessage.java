package christmas.domain.exception;

public enum ErrorMessage {
    ERROR_MESSAGE_HEADER("[ERROR] "),
    DATE_ERROR_MESSAGE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    ORDER_TOTAL_QUANTITY_MAXIMUM_ERROR_MESSAGE("한 주문 당 최대 20개까지만 주문 가능합니다. 다시 입력해 주세요."),
    ORDER_NOT_VALID_ERROR_MESSAGE("유효하지 않은 주문입니다. 다시 입력해 주세요.");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return ERROR_MESSAGE_HEADER.errorMessage + errorMessage;
    }
}
