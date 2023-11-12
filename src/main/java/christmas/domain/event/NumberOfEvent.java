package christmas.domain.event;

public enum NumberOfEvent {
    ZERO(0),
    ONE_DAY(1),
    HUNDRED_WON(100),
    THOUSAND_WON(1000),
    TEN_THOUSAND_WON(10000),
    DISCOUNT_UNIT(2023),
    CHAMPAGNE_PRICE(25000),
    EVENT_MIN_TOTAL_PRICE(120000);
    private final int numberOfEvent;

    NumberOfEvent(int numberOfEvent) {
        this.numberOfEvent = numberOfEvent;
    }

    public int getNumberOfEvent() {
        return numberOfEvent;
    }
}
