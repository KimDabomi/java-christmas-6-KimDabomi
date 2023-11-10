package christmas.domain.exception;

public enum ErrorMessage {
    ERROR_MESSAGE_HEADER("[ERROR] "),
    DATE_TYPE_ERROR_MESSAGE("날짜는 숫자 형식으로만 입력 가능합니다. 다시 입력해 주세요."),
    DATE_RANGE_ERROR_MESSAGE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    TOTAL_AMOUNT_MINIMUN_ERROR_MESSAGE("최소 주문 금액은 10,000원 이상이여야 합니다. 다시 입력해 주세요."),
    ORDER_ONLY_DRINK_ERROR_MESSAGE("음료만 주문은 불가능합니다. 다시 입력해 주세요."),
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
