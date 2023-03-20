package in.mypractice.food.ordering.service.service;

import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.entity.Restaurant;
import in.mypractice.food.ordering.service.event.OrderCancelledEvent;
import in.mypractice.food.ordering.service.event.OrderCreatedEvent;
import in.mypractice.food.ordering.service.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
  OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);
  OrderPaidEvent payOrder(Order order);
  void approveOrder(Order order);

  OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage);
  void cancelOrder(Order order, List<String> failureMessage);
}
