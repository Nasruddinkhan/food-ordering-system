package in.mypractice.food.ordering.service.service;

import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.entity.Restaurant;
import in.mypractice.food.ordering.service.event.OrderCancelledEvent;
import in.mypractice.food.ordering.service.event.OrderCreatedEvent;
import in.mypractice.food.ordering.service.event.OrderPaidEvent;
import in.mypractice.food.ordering.system.domain.events.publisher.DomainEventPublisher;

import java.util.List;

public interface OrderDomainService {
  OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant, DomainEventPublisher<OrderCreatedEvent> eventPublisher);
  OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> eventPublisher);
  void approveOrder(Order order);

  OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage, DomainEventPublisher<OrderCancelledEvent> eventPublisher);
  void cancelOrder(Order order, List<String> failureMessage);
}
