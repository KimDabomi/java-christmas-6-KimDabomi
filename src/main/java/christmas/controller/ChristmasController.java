package christmas.controller;

import christmas.domain.order.Order;
import christmas.view.InputView;
import christmas.view.OutputView;

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
                String dateInput = inputView.readDate();
                int date = Order.getDate(dateInput);

                startOrder();

                outputView.showEventPreview(date);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void startOrder() {
        while (true) {
            try {
                Order order = inputView.readMenuOrder();
                outputView.showOrderList(order);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
