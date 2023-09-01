package in.mypractice.food.ordering.service.event;

import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.system.domain.events.publisher.DomainEventPublisher;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent {
    private final DomainEventPublisher<OrderPaidEvent> eventPublisher;

    public OrderPaidEvent(Order order, ZonedDateTime createAt, DomainEventPublisher<OrderPaidEvent> eventPublisher) {
        super(order, createAt);
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void fire() {
      eventPublisher.publish(this);
    }
}
