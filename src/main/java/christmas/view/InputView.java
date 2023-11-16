package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.order.OrderInformation;
import christmas.domain.order.OrderItem;
import christmas.domain.order.Order;

import java.util.ArrayList;
import java.util.List;

public class InputView {
    public InputView() {
    }

    public String readDate() {
        System.out.println(ViewMessage.START_MESSAGE.getViewMessage());
        System.out.println(ViewMessage.ASK_DATE_MESSAGE.getViewMessage());

        return Console.readLine();
    }

    public Order readMenuOrder() {
        System.out.println(ViewMessage.ASK_ORDER_MENU_QUANTITY_MESSAGE.getViewMessage());
        String[] menuItems = Console.readLine().split(ViewMessage.SPLIT_CRITERIA.getViewMessage());
        List<OrderItem> orderItemList = new ArrayList<>();

        return OrderInformation.getOrderItems(menuItems, orderItemList);
    }
}
