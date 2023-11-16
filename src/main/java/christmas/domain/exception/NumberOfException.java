package christmas.domain.exception;

public enum NumberOfException {
    MIN_DATE(1),
    MAX_DATE(31),
    MIN_QUANTITY(1),
    MAX_QUANTITY(20),
    ORDER_LIST_SIZE(2);

    private final int numberOfException;

    NumberOfException(int numberOfException) {
        this.numberOfException = numberOfException;
    }

    public int getNumber() {
        return numberOfException;
    }
}
