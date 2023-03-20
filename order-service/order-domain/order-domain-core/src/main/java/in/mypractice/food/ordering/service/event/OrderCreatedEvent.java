package in.mypractice.food.ordering.service.event;

import in.mypractice.food.ordering.service.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent {

    public OrderCreatedEvent(Order order, ZonedDateTime createAt) {
        super(order, createAt);
    }

}
