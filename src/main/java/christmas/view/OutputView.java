package christmas.view;

import christmas.domain.event.DiscountEvent;
import christmas.domain.event.EventBadge;
import christmas.domain.event.NumberOfEvent;
import christmas.domain.order.Order;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class OutputView {
    private Order order;

    public OutputView() {
    }

    public void showOrderItems() {
        if (order != null) {
            order.printOrderItems();
        }
    }

    public void showEventPreview(int date) {
        System.out.printf(ViewMessage.SHOW_PREVIEW_EVENT_MESSAGE.getViewMessage(), date);
        System.out.println();
    }

    public void showOrderList(Order order) {
        if (order != null) {
            System.out.println(ViewMessage.SHOW_ORDER_MENU_MESSAGE.getViewMessage());
            order.printOrderList();
        }
    }

    public void showTotalAmountBeforeDiscount(Order order) {
        if (order != null) {
            System.out.println(ViewMessage.SHOW_BEFORE_DISCOUNT_AMOUNT_MESSAGE.getViewMessage());
            String totalAmountBeforeDiscount = formatAmount(order.getTotalAmountBeforeDiscount());
            System.out.printf(ViewMessage.SHOW_STRING_AMOUNT_MESSAGE.getViewMessage(), totalAmountBeforeDiscount);
            System.out.println();
        }
    }

    public void showGiftMenu(Order order, LocalDate date) {
        int champagneDiscount = DiscountEvent.GIFT_CHAMPAGNE.calculateDiscount(order, date);
        System.out.println(ViewMessage.SHOW_GIFT_TITLE_MESSAGE.getViewMessage());

        if (champagneDiscount > NumberOfEvent.ZERO.getNumberOfEvent()) {
            System.out.println(ViewMessage.SHOW_GIFT_MENU_MESSAGE.getViewMessage());
        }

        if (champagneDiscount == NumberOfEvent.ZERO.getNumberOfEvent()){
            System.out.println(ViewMessage.SHOW_NONE_MESSAGE.getViewMessage());
        }
    }

    public void showDiscountList(Order order, LocalDate date) {
        System.out.println(ViewMessage.SHOW_EVENT_LIST_TITLE_MESSAGE.getViewMessage());

        if (order.getTotalAmountBeforeDiscount() >= NumberOfEvent.TEN_THOUSAND_WON.getNumberOfEvent()) {
            showDiscount(order, date);
        }

        if (order.getTotalAmountBeforeDiscount() < NumberOfEvent.TEN_THOUSAND_WON.getNumberOfEvent()) {
            System.out.println(ViewMessage.SHOW_NONE_MESSAGE.getViewMessage());
        }
    }

    public void showTotalDiscountAmount(Order order, LocalDate date) {
        System.out.println(ViewMessage.SHOW_TOTAL_EVENT_TITLE_MESSAGE.getViewMessage());
        int totalAmount = DiscountEvent.getTotalDiscountAmount(order, date);
        String totalDiscountAmount = formatAmount(totalAmount);

        if (order.getTotalAmountBeforeDiscount() >= NumberOfEvent.TEN_THOUSAND_WON.getNumberOfEvent()) {
            System.out.printf(ViewMessage.SHOW_MINUS_AMOUNT_MESSAGE.getViewMessage(), totalDiscountAmount);
            System.out.println();
        }

        if (order.getTotalAmountBeforeDiscount() < NumberOfEvent.TEN_THOUSAND_WON.getNumberOfEvent()) {
            totalAmount = 0;
            System.out.printf(ViewMessage.SHOW_INT_AMOUNT_MESSAGE.getViewMessage(), totalAmount);
            System.out.println();
        }
    }

    public void showFinalAmount(Order order, LocalDate date) {
        int finalAmount = DiscountEvent.getTotalAmount(order, date);
        String totalFinalAmount = formatAmount(finalAmount);

        System.out.println(ViewMessage.SHOW_FINAL_AMOUNT_MESSAGE.getViewMessage());
        System.out.printf(ViewMessage.SHOW_STRING_AMOUNT_MESSAGE.getViewMessage(), totalFinalAmount);
    }

    public void showBadge(Order order, LocalDate date) {
        int totalAmount = DiscountEvent.getTotalDiscountAmount(order, date);
        System.out.println();
        System.out.println(ViewMessage.SHOW_BADGE_MESSAGE.getViewMessage());

        if (totalAmount > 0) {
            System.out.println(EventBadge.getBadgeForDiscount(totalAmount));
        }

        if (totalAmount == 0) {
            System.out.println(ViewMessage.SHOW_NONE_MESSAGE.getViewMessage());
        }
    }

    private void showDiscount(Order order, LocalDate date) {
        showChristmasDiscount(date);
        showWeekdayDiscount(order, date);
        showWeekendDiscount(date);
        showSpecialDiscount(date);
        showChampagneGift(order, date);
    }

    private void showChristmasDiscount(LocalDate date) {
        String christmasDiscountAmount = formatAmount(DiscountEvent.CHRISTMAS_D_DAY.calculateDiscount(order, date));

        if (!christmasDiscountAmount.equals(ViewMessage.NONE.getViewMessage())) {
            System.out.printf(ViewMessage.SHOW_CHRISTMAS_EVENT_MESSAGE.getViewMessage(), christmasDiscountAmount);
            System.out.println();
        }
    }

    private void showWeekdayDiscount(Order order, LocalDate date) {
        String weekdayDiscountAmount = formatAmount(DiscountEvent.WEEKDAY_DISCOUNT.calculateDiscount(order, date));

        if (!weekdayDiscountAmount.equals(ViewMessage.NONE.getViewMessage())) {
            System.out.printf(ViewMessage.SHOW_WEEKDAY_EVENT_MESSAGE.getViewMessage(), weekdayDiscountAmount);
            System.out.println();
        }
    }

    private void showWeekendDiscount(LocalDate date) {
        String weekendDiscountAmount = formatAmount(DiscountEvent.WEEKEND_DISCOUNT.calculateDiscount(order, date));

        if (!weekendDiscountAmount.equals(ViewMessage.NONE.getViewMessage())) {
            System.out.printf(ViewMessage.SHOW_WEEKEND_EVENT_MESSAGE.getViewMessage(), weekendDiscountAmount);
            System.out.println();
        }
    }

    private void showSpecialDiscount(LocalDate date) {
        String specialDiscountAmount = formatAmount(DiscountEvent.SPECIAL_DISCOUNT.calculateDiscount(order, date));

        if (!specialDiscountAmount.equals(ViewMessage.NONE.getViewMessage())) {
            System.out.printf(ViewMessage.SHOW_SPECIAL_EVENT_MESSAGE.getViewMessage(), specialDiscountAmount);
            System.out.println();
        }
    }

    private void showChampagneGift(Order order, LocalDate date) {
        String champagneGiftAmount = formatAmount(DiscountEvent.GIFT_CHAMPAGNE.calculateDiscount(order, date));

        if (!champagneGiftAmount.equals(ViewMessage.NONE.getViewMessage())) {
            System.out.printf(ViewMessage.SHOW_GIFT_EVENT_MESSAGE.getViewMessage(), champagneGiftAmount);
            System.out.println();
        }
    }

    private String formatAmount(int amount) {
        DecimalFormat decFormat = new DecimalFormat(ViewMessage.AMOUNT_FORMAT.getViewMessage());
        return decFormat.format(amount);
    }
}
