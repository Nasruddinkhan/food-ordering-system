package in.mypractice.food.ordering.service.service.impl;

import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.entity.Restaurant;
import in.mypractice.food.ordering.service.event.OrderCancelledEvent;
import in.mypractice.food.ordering.service.event.OrderCreatedEvent;
import in.mypractice.food.ordering.service.event.OrderPaidEvent;
import in.mypractice.food.ordering.service.exception.OrderDomainException;
import in.mypractice.food.ordering.service.service.OrderDomainService;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("order with id : {} is initiate", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(restaurantProduct -> {
            var currentProduct  = orderItem.getProduct();
            if (currentProduct.equals(restaurantProduct)){
                currentProduct.updateWithConfirmNameAndPrice(restaurantProduct.getName(), restaurantProduct.getPrice());
            }
        }));
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()) throw new OrderDomainException("restaurant with id "+ restaurant.getId().getValue()+
                " is currently not active");

    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("order with id: {}", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("order with id: {}", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage) {
        order.initCancel( failureMessage);
        log.info("order with id: {}", order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));

    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessage) {
        order.cancel( failureMessage);
        log.info("order with id: {}", order.getId().getValue());
    }
}
