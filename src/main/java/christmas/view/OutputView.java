package christmas.view;

import christmas.domain.event.DiscountEvent;
import christmas.domain.event.NumberOfEvent;
import christmas.domain.order.Order;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class OutputView {
    private Order order;

    public OutputView() {
    }

    public void showOrderItems(Order order) {
        if (order != null) {
            order.printOrderItems();
        }
    }

    public void showEventPreview(int date) {
        System.out.printf("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!", date);
        System.out.println();
    }

    public void showOrderList(Order order) {
        if (order != null) {
            System.out.println("\n<주문 메뉴>");
            order.printOrderList();
        }
    }

    public void showTotalAmountBeforeDiscount(Order order) {
        if (order != null) {
            System.out.println("<할인 전 총주문 금액>");
            String totalAmountBeforeDiscount = formatAmount(order.getTotalAmountBeforeDiscount());
            System.out.printf("%s원", totalAmountBeforeDiscount);
        }
    }

    public void showGiftMenu(Order order, LocalDate date) {
        int champagneDiscount = DiscountEvent.GIFT_CHAMPAGNE.calculateDiscount(order, date);

        if (champagneDiscount > NumberOfEvent.ZERO.getNumberOfEvent()) {
            System.out.println("\n\n<증정 메뉴>\n샴페인 1개");
        }

        if (champagneDiscount == NumberOfEvent.ZERO.getNumberOfEvent()){
            System.out.println("\n\n<증정 메뉴>\n없음");
        }
    }

    public void showDiscountList(Order order, LocalDate date) {
        System.out.println();
        System.out.println("<혜택 내역>");
        if (order.getTotalAmountBeforeDiscount() >= NumberOfEvent.TEN_THOUSAND_WON.getNumberOfEvent()) {
            showDiscount(order, date);
        }
        if (order.getTotalAmountBeforeDiscount() < NumberOfEvent.TEN_THOUSAND_WON.getNumberOfEvent()) {
            System.out.println("없음");
        }
    }

    public void showTotalDiscountAmount(Order order, LocalDate date) {
        System.out.println("\n<총혜택 금액>");
        int totalAmount = DiscountEvent.getTotalDiscountAmount(order, date);
        String totalDiscountAmount = formatAmount(totalAmount);

        if (order.getTotalAmountBeforeDiscount() >= NumberOfEvent.TEN_THOUSAND_WON.getNumberOfEvent()) {
            System.out.printf("-%s원", totalDiscountAmount);
            System.out.println();
        }
        if (order.getTotalAmountBeforeDiscount() < NumberOfEvent.TEN_THOUSAND_WON.getNumberOfEvent()) {
            totalAmount = 0;
            System.out.printf("%d원", totalAmount);
            System.out.println();
        }
    }

    public void showFinalAmount(Order order, LocalDate date) {
        int finalAmount = DiscountEvent.getTotalAmount(order, date);
        String totalFinalAmount = formatAmount(finalAmount);

        System.out.println("\n<할인 후 예상 결제 금액>");
        System.out.printf("%s원", totalFinalAmount);
    }

    private void showDiscount(Order order, LocalDate date) {
        showChristmasDiscount(order, date);
        showWeekdayDiscount(order, date);
        showWeekendDiscount(order, date);
        showSpecialDiscount(order, date);
        showChampagneGift(order, date);
    }

    private void showChristmasDiscount(Order order, LocalDate date) {
        String christmasDiscountAmount = formatAmount(DiscountEvent.CHRISTMAS_D_DAY.calculateDiscount(order, date));

        if (!christmasDiscountAmount.equals("0")) {
            System.out.printf("크리스마스 디데이 할인: -%s원", christmasDiscountAmount);
            System.out.println();
        }
    }

    private void showWeekdayDiscount(Order order, LocalDate date) {
        String weekdayDiscountAmount = formatAmount(DiscountEvent.WEEKDAY_DISCOUNT.calculateDiscount(order, date));
        if (!weekdayDiscountAmount.equals("0")) {
            System.out.printf("평일 할인: -%s원", weekdayDiscountAmount);
            System.out.println();
        }
    }

    private void showWeekendDiscount(Order order, LocalDate date) {
        String weekendDiscountAmount = formatAmount(DiscountEvent.WEEKEND_DISCOUNT.calculateDiscount(order, date));
        if (!weekendDiscountAmount.equals("0")) {
            System.out.printf("주말 할인: -%s원", weekendDiscountAmount);
            System.out.println();
        }
    }

    private void showSpecialDiscount(Order order, LocalDate date) {
        String specialDiscountAmount = formatAmount(DiscountEvent.SPECIAL_DISCOUNT.calculateDiscount(order, date));
        if (!specialDiscountAmount.equals("0")) {
            System.out.printf("특별 할인: -%s원", specialDiscountAmount);
            System.out.println();
        }
    }

    private void showChampagneGift(Order order, LocalDate date) {
        String champagneGiftAmount = formatAmount(DiscountEvent.GIFT_CHAMPAGNE.calculateDiscount(order, date));
        if (!champagneGiftAmount.equals("0")) {
            System.out.printf("증정 이벤트: -%s원", champagneGiftAmount);
            System.out.println();
        }
    }

    private String formatAmount(int amount) {
        DecimalFormat decFormat = new DecimalFormat("###,###");
        return decFormat.format(amount);
    }
}
