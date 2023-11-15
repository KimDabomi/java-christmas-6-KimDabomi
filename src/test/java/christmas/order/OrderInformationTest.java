package christmas.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.domain.exception.ErrorMessage;
import christmas.domain.order.OrderInformation;
import christmas.domain.order.OrderItem;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderInformationTest {
    private static Stream<Arguments> provideOrderExceptionsTestData() {
        return Stream.of(
                Arguments.of(new String[]{"양송이수프2"}, ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE),
                Arguments.of(new String[]{"제로콜라-2", "샴페인-3"}, ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE),
                Arguments.of(new String[]{"티본스테이크-22"}, ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE),
                Arguments.of(new String[]{"티본스테이크-0"}, ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE),
                Arguments.of(new String[]{"티본스테이크-1", "티본스테이크-1"}, ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE),
                Arguments.of(new String[]{"라면-1"}, ErrorMessage.ORDER_NOT_VALID_ERROR_MESSAGE)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"a3", "40", "-123"})
    @DisplayName("형식에 벗어난 날짜를 입력한 경우 예외 확인")
    void getDate_범위가_아닌_날짜(String date) {
        assertThatThrownBy(() -> OrderInformation.getDate(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorMessage.DATE_ERROR_MESSAGE.getErrorMessage());
    }

    @ParameterizedTest(name = "{index} - 주문 내역: {0}, 예상 결과: {1}")
    @MethodSource("provideOrderExceptionsTestData")
    @DisplayName("주문 예외 상황 테스트")
    void testOrderExceptions(String[] menuItems, ErrorMessage errorMessage) {
        List<OrderItem> actualOrderItems = new ArrayList<>();
        assertThatThrownBy(() -> OrderInformation.getOrderItems(menuItems, actualOrderItems))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(errorMessage.getErrorMessage());
    }

    @Test
    @DisplayName("날짜 확인")
    void getDate_확인() {
        String date = "3";
        int resultDate = OrderInformation.getDate(date);
        assertThat(resultDate).isEqualTo(3);
    }

    @Test
    @DisplayName("주문리스트 확인")
    void getOrderItems_주문리스트_확인() {
        String[] menuItems = {"양송이수프-2", "티본스테이크-3"};
        List<OrderItem> actualOrderItems = new ArrayList<>();
        OrderInformation.getOrderItems(menuItems, actualOrderItems);
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
}
