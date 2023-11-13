package christmas.controller;

import christmas.domain.order.Order;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;

public class ChristmasController {
    private final InputView inputView;
    private final OutputView outputView;

    public ChristmasController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        startDate();
    }

    private void startDate() {
        while (true) {
            try {
                startOrder();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void startOrder() {
        while (true) {
            try {
                String dateInput = inputView.readDate();
                int date = Order.getDate(dateInput);
                LocalDate localDate = LocalDate.ofEpochDay(date);
                Order order = inputView.readMenuOrder();
                printOrder(order, date);
                startDiscountList(order, localDate);
                startFinalAmount(order, localDate);
                startBadge(order, localDate);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printOrder(Order order, int date) {
        outputView.showOrderItems(order);
        outputView.showEventPreview(date);
        outputView.showOrderList(order);
        outputView.showTotalAmountBeforeDiscount(order);
        outputView.showGiftMenu(order, LocalDate.ofEpochDay(date));
    }

    private void startDiscountList(Order order, LocalDate date) {
        outputView.showDiscountList(order, date);
        outputView.showTotalDiscountAmount(order, date);
    }

    private void startFinalAmount(Order order, LocalDate date) {
        outputView.showFinalAmount(order, date);
    }

    private void startBadge(Order order, LocalDate date) {
        outputView.showBadge(order, date);
    }
}
