package christmas.domain.exception;

public enum Restriction {
    MIN_DATE(1),
    MAX_DATE(31),
    MIN_AMOUNT(10000),
    MIN_QUANTITY(1),
    MAX_QUANTITY(20),
    ORDER_LIST_SIZE(2);

    private final int number;

    Restriction(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
