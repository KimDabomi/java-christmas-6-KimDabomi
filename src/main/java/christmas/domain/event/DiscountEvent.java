package christmas.domain.event;

import christmas.domain.order.Order;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public enum DiscountEvent {
    CHRISTMAS_D_DAY {
        private final int[] christmasDays = {1, 25};

        @Override
        public int calculateDiscount(Order order, LocalDate date) {
            int dayOfMonth = date.getDayOfMonth() - NumberOfEvent.ONE_DAY.getNumberOfEvent();
            if (christmasDays[0] > dayOfMonth || christmasDays[1] < dayOfMonth) {
                return NumberOfEvent.ZERO.getNumberOfEvent();
            }

            return (dayOfMonth - NumberOfEvent.ONE_DAY.getNumberOfEvent()) * NumberOfEvent.HUNDRED_WON.getNumberOfEvent() + NumberOfEvent.THOUSAND_WON.getNumberOfEvent();
        }
    },
    WEEKDAY_DISCOUNT {
        @Override
        public int calculateDiscount(Order order, LocalDate date) {
            if (isWeekday(date)) {
                return order.getTotalQuantityForCategory("디저트") * NumberOfEvent.DISCOUNT_UNIT.getNumberOfEvent();
            }

            return NumberOfEvent.ZERO.getNumberOfEvent();
        }
    },
    WEEKEND_DISCOUNT {
        @Override
        public int calculateDiscount(Order order, LocalDate date) {
            if (isWeekend(date)) {
                return order.getTotalQuantityForCategory("메인") * NumberOfEvent.DISCOUNT_UNIT.getNumberOfEvent();
            }

            return NumberOfEvent.ZERO.getNumberOfEvent();
        }
    },
    SPECIAL_DISCOUNT {
        private final List<Integer> specialDays = List.of(3, 10, 17, 24, 25, 31);

        @Override
        public int calculateDiscount(Order order, LocalDate date) {
            if (specialDays.contains(date.getDayOfMonth() - 1)) {
                return NumberOfEvent.THOUSAND_WON.getNumberOfEvent();
            }

            return NumberOfEvent.ZERO.getNumberOfEvent();
        }
    },
    GIFT_CHAMPAGNE {
        @Override
        public int calculateDiscount(Order order, LocalDate date) {
            if (order.getTotalAmountBeforeDiscount() >= NumberOfEvent.EVENT_MIN_TOTAL_PRICE.getNumberOfEvent()) {
                return NumberOfEvent.CHAMPAGNE_PRICE.getNumberOfEvent();
            }

            return NumberOfEvent.ZERO.getNumberOfEvent();
        }
    };

    public abstract int calculateDiscount(Order order, LocalDate date);

    public static int getTotalDiscountAmount(Order order, LocalDate date) {
        int christmasDiscountAmount = DiscountEvent.CHRISTMAS_D_DAY.calculateDiscount(order, date);
        int weekdayDiscountAmount = DiscountEvent.WEEKDAY_DISCOUNT.calculateDiscount(order, date);
        int weekendDiscountAmount = DiscountEvent.WEEKEND_DISCOUNT.calculateDiscount(order, date);
        int specialDiscountAmount = DiscountEvent.SPECIAL_DISCOUNT.calculateDiscount(order, date);
        int champagneGiftAmount = DiscountEvent.GIFT_CHAMPAGNE.calculateDiscount(order, date);
        int totalDiscountAmount = christmasDiscountAmount + weekdayDiscountAmount + weekendDiscountAmount + specialDiscountAmount + champagneGiftAmount;

        if (order.getTotalAmountBeforeDiscount() < NumberOfEvent.TEN_THOUSAND_WON.getNumberOfEvent()) {
            totalDiscountAmount = NumberOfEvent.ZERO.getNumberOfEvent();
        }

        return totalDiscountAmount;
    }

    public static int getTotalAmount(Order order, LocalDate date) {
        int totalBeforeDiscountAmount = order.getTotalAmountBeforeDiscount();
        int totalDiscountAmount = DiscountEvent.getTotalDiscountAmount(order, date);

        return totalBeforeDiscountAmount - totalDiscountAmount + DiscountEvent.GIFT_CHAMPAGNE.calculateDiscount(order, date);
    }

    private static boolean isWeekday(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.FRIDAY && day != DayOfWeek.SATURDAY;
    }

    private static boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY;
    }
}
