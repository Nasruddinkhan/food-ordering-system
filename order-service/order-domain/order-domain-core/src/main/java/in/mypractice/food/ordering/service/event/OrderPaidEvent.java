package in.mypractice.food.ordering.service.event;

import in.mypractice.food.ordering.service.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent {

    public OrderPaidEvent(Order order, ZonedDateTime createAt) {
        super(order, createAt);
    }
}
