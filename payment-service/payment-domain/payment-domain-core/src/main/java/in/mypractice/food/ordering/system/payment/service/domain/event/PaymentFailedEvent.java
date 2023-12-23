package in.mypractice.food.ordering.system.payment.service.domain.event;

import in.mypractice.food.ordering.system.domain.events.publisher.DomainEventPublisher;
import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentFailedEvent extends PaymentEvent{
    private final DomainEventPublisher<PaymentFailedEvent> eventPublisher;
    public PaymentFailedEvent(Payment payment, List<String> failureMessages, ZonedDateTime createdAt,
                              DomainEventPublisher<PaymentFailedEvent> eventPublisher) {
        super(payment, failureMessages, createdAt);
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void fire() {
        this.eventPublisher.publish(this);
    }
}
