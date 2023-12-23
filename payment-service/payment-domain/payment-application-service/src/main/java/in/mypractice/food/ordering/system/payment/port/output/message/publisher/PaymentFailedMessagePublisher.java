package in.mypractice.food.ordering.system.payment.port.output.message.publisher;

import in.mypractice.food.ordering.system.domain.events.publisher.DomainEventPublisher;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;

public interface PaymentFailedMessagePublisher extends DomainEventPublisher<PaymentFailedEvent> {

}
