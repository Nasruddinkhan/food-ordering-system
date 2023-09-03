package in.mypractice.food.ordering.system.restaurant.domain.service;

import in.mypractice.food.ordering.system.domain.events.publisher.DomainEventPublisher;
import in.mypractice.food.ordering.system.restaurant.domain.entity.Restaurant;
import in.mypractice.food.ordering.system.restaurant.domain.event.OrderApprovalEvent;
import in.mypractice.food.ordering.system.restaurant.domain.event.OrderApprovedEvent;
import in.mypractice.food.ordering.system.restaurant.domain.event.OrderRejectedEvent;

import java.util.List;

public interface RestaurantDomainService {
    OrderApprovalEvent validateOrder(Restaurant restaurant,
                                     List<String> failureMessages,
                                     DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher,
                                     DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher);

}
