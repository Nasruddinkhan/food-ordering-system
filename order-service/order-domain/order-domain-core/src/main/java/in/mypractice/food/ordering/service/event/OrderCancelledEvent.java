package in.mypractice.food.ordering.service.event;

import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.system.domain.events.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends OrderEvent {
    private final DomainEventPublisher<OrderCancelledEvent> eventPublisher;
    public OrderCancelledEvent(Order order, ZonedDateTime createAt, DomainEventPublisher<OrderCancelledEvent> eventPublisher) {
        super(order, createAt);
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void fire() {
        eventPublisher.publish(this);
    }
}
