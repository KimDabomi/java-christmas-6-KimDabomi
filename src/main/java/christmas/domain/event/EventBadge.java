package christmas.domain.event;

public enum EventBadge {
    STAR(5000, "별"),
    TREE(10000, "트리"),
    SANTA(20000, "산타");

    private final int totalDiscountAmount;
    private final String badge;

    EventBadge(int totalDiscountAmount, String badge) {
        this.totalDiscountAmount = totalDiscountAmount;
        this.badge = badge;
    }

    public static String getBadgeForDiscount(int totalDiscount) {
        for (EventBadge eventBadge : EventBadge.values()) {
            if (totalDiscount >= eventBadge.totalDiscountAmount) {
                return eventBadge.badge;
            }
        }
        return "없음";
    }
}
