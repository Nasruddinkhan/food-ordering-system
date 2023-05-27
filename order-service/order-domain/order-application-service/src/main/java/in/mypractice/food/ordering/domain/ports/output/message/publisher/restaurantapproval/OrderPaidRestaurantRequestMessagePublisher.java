package in.mypractice.food.ordering.domain.ports.output.message.publisher.restaurantapproval;

import in.mypractice.food.ordering.domain.events.publisher.DomainEventPublisher;
import in.mypractice.food.ordering.service.event.OrderCreatedEvent;
import in.mypractice.food.ordering.service.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {

}
