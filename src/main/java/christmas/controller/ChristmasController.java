package christmas.controller;

import christmas.domain.order.Order;
import christmas.domain.order.OrderInformation;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.time.LocalDate;
import java.util.function.Supplier;

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
        executeWithRetry(() -> {
            processOrder();
            return null;
        });
    }

    private void processOrder() {
        executeWithRetry(() -> {
            String dateInput = inputView.readDate();
            int date = OrderInformation.getDate(dateInput);
            LocalDate localDate = LocalDate.ofEpochDay(date);

            processEvent(date, localDate);
            return null;
        });
    }

    private void processEvent(int date, LocalDate localDate) {
        executeWithRetry(() -> {
            Order order = inputView.readMenuOrder();

            printOrder(order, date);
            startDiscountList(order, localDate);
            startFinalAmount(order, localDate);
            startBadge(order, localDate);

            return null;
        });
    }

    private void printOrder(Order order, int date) {
        outputView.showOrderItems();
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

    private <T> T executeWithRetry(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
