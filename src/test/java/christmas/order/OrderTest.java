package christmas.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.menu.Menu;
import christmas.domain.exception.ErrorMessage;
import christmas.domain.order.Order;
import christmas.domain.order.OrderItem;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderTest {
    private void testDateExceptions(String date, ErrorMessage errorMessage) {
        assertThatThrownBy(() -> Order.getDate(date))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining(errorMessage.getErrorMessage());
    }
    private void testOrderExceptions(String[] menuItems, ErrorMessage errorMessage) {
        List<OrderItem> actualOrderItems = new ArrayList<>();

        assertThatThrownBy(() -> Order.getOrderItems(menuItems, actualOrderItems))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining(errorMessage.getErrorMessage());
    }

    @Test
    @DisplayName("날짜 확인")
    void getDate_확인() {
        String date = "3";
        int resultDate = Order.getDate(date);
        assertThat(resultDate).isEqualTo(3);
    }

    @Test
    @DisplayName("날짜에 문자 입력한 경우 예외 확인")
    void getDate_날짜에_문자_입력한_경우() {
        String date = "a3";
        testDateExceptions(date, ErrorMessage.DATE_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("1~31 범위가 아닌 날짜를 입력한 경우 예외 확인")
    void getDate_1_31_범위가_아닌_날짜를_입력한_경우() {
        String date = "40";
        testDateExceptions(date, ErrorMessage.DATE_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("주문리스트 확인")
    void getOrderItems_주문리스트_확인() {
        String[] menuItems = {"양송이수프-2", "티본스테이크-3"};
        List<OrderItem> actualOrderItems = new ArrayList<>();
        Order.getOrderItems(menuItems, actualOrderItems);
        assertThat(actualOrderItems.size()).isEqualTo(2);

        assertThat(actualOrderItems.get(0).getMenu().getCategory()).isEqualTo("에피타이저");
        assertThat(actualOrderItems.get(0).getMenu().getFoodName()).isEqualTo("양송이수프");
        assertThat(actualOrderItems.get(0).getMenu().getPrice()).isEqualTo(6000);
        assertThat(actualOrderItems.get(0).getQuantity()).isEqualTo(2);

        assertThat(actualOrderItems.get(1).getMenu().getCategory()).isEqualTo("메인");
        assertThat(actualOrderItems.get(1).getMenu().getFoodName()).isEqualTo("티본스테이크");
        assertThat(actualOrderItems.get(1).getMenu().getPrice()).isEqualTo(55000);
        assertThat(actualOrderItems.get(1).getQuantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("메뉴 입력 형식 틀린 경우 예외 확인")
    void getOrderItems_메뉴_입력_형식_틀린_경우() {
        String[] menuItems = {"양송이수프2"};
        List<OrderItem> actualOrderItems = new ArrayList<>();

        assertThatThrownBy(() -> Order.getOrderItems(menuItems, actualOrderItems))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining(ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE.getErrorMessage());
    }

    @Test
    @DisplayName("음료만 시킨 경우 예외 확인")
    void getOrderItems_음료만_시킨_경우() {
        String[] menuItems = {"제로콜라-2", "샴페인-3"};

        testOrderExceptions(menuItems, ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("총 주문 수량이 20개 초과인 경우 예외 확인")
    void getOrderItems_총_주문_수량_20개_초과인_경우() {
        String[] menuItems = {"티본스테이크-22"};

        testOrderExceptions(menuItems, ErrorMessage.ORDER_TOTAL_QUANTITY_MAXIMUM_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("총 주문 수량이 1개 미만인 경우 예외 확인")
    void getOrderItems_총_주문_수량이_1개_미만인_경우() {
        String[] menuItems = {"티본스테이크-0"};

        testOrderExceptions(menuItems, ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("중복 주문인 경우 예외 확인")
    void getOrderItems_중복_주문인_경우() {
        String[] menuItems = {"티본스테이크-1", "티본스테이크-1"};

        testOrderExceptions(menuItems, ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("존재하지 않는 메뉴인 경우 예외 확인")
    void getOrderItems_존재하지_않는_메뉴인_경우() {
        String[] menuItems = {"라면-1"};

        testOrderExceptions(menuItems, ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("할인 전 총 금액 계산")
    void getTotalPriceBeforeDiscount_할인_전_총_금액_확인() {
        OrderItem orderItem = new OrderItem(Menu.T_BONE_STEAK, "4");
        List<OrderItem> orderItemsList = new ArrayList<OrderItem>();
        Order order = new Order(orderItemsList);
        orderItemsList.add(orderItem);
        int totalPrice = order.getTotalAmountBeforeDiscount();
        assertThat(totalPrice).isEqualTo(220000);
    }
}
