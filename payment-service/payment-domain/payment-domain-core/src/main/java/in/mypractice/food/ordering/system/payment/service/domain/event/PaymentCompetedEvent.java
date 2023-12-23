package in.mypractice.food.ordering.system.payment.service.domain.event;

import in.mypractice.food.ordering.system.domain.events.publisher.DomainEventPublisher;
import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentCompetedEvent extends PaymentEvent {
    private final DomainEventPublisher<PaymentCompetedEvent> eventPublisher;

    public PaymentCompetedEvent(Payment payment, ZonedDateTime createdAt, DomainEventPublisher<PaymentCompetedEvent> eventPublisher) {
        super(payment, List.of(), createdAt);
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void fire() {
        eventPublisher.publish(this);
    }
}
