package in.mypractice.food.ordering.domain.ports.output.message.publisher.payment;

import in.mypractice.food.ordering.domain.events.publisher.DomainEventPublisher;
import in.mypractice.food.ordering.service.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {

}
