package in.mypractice.food.ordering.service.event;

import in.mypractice.food.ordering.service.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends OrderEvent {

    public OrderCancelledEvent(Order order, ZonedDateTime createAt) {
        super(order, createAt);
    }
}
