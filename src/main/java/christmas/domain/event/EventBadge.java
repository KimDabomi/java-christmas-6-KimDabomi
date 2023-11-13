package christmas.domain.event;

import christmas.view.ViewMessage;

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
        if (totalDiscount >= STAR.totalDiscountAmount && totalDiscount < TREE.totalDiscountAmount) {
            return STAR.badge;
        }

        if (totalDiscount >= TREE.totalDiscountAmount && totalDiscount < SANTA.totalDiscountAmount) {
            return TREE.badge;
        }

        if (totalDiscount >= SANTA.totalDiscountAmount) {
            return SANTA.badge;
        }

        return ViewMessage.SHOW_NONE_MESSAGE.getViewMessage();
    }
}
