package in.mypractice.food.ordering.system.payment.service.domain.event;

import in.mypractice.food.ordering.system.domain.events.publisher.DomainEventPublisher;
import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentCancelledEvent extends PaymentEvent{
    private final DomainEventPublisher<PaymentCancelledEvent> eventPublisher;

    public PaymentCancelledEvent(Payment payment, ZonedDateTime createdAt, DomainEventPublisher<PaymentCancelledEvent> eventPublisher) {
        super(payment, List.of(), createdAt);
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void fire() {
        this.eventPublisher.publish(this);
    }
}
