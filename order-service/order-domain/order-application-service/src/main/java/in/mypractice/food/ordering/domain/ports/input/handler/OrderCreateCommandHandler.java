package in.mypractice.food.ordering.domain.ports.input.handler;


import in.mypractice.food.ordering.domain.dto.create.CreateOrderCommand;
import in.mypractice.food.ordering.domain.dto.create.CreateOrderResponse;
import in.mypractice.food.ordering.domain.mapper.OrderDataMapper;
import in.mypractice.food.ordering.domain.ports.output.message.publisher.ApplicationDomainEventPublisher;
import in.mypractice.food.ordering.domain.ports.output.repository.CustomerRepository;
import in.mypractice.food.ordering.domain.ports.output.repository.OrderRepository;
import in.mypractice.food.ordering.domain.ports.output.repository.RestaurantRepository;
import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.entity.Restaurant;
import in.mypractice.food.ordering.service.exception.OrderDomainException;
import in.mypractice.food.ordering.service.service.OrderDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreateCommandHandler {
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    private final RestaurantRepository restaurantRepository;

    private final OrderDataMapper orderDataMapper;

    private final ApplicationDomainEventPublisher applicationDomainEventPublisher;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        var restaurant = checkRestaurant(createOrderCommand);
        var order = orderDataMapper.createOrder(createOrderCommand);
        var orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        var saveOrder = saveOrder(order);
        applicationDomainEventPublisher.publish(orderCreatedEvent);

        return orderDataMapper.createOrderResponse(saveOrder);
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
      var restaurant =  orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
       var restaurantInfo =   restaurantRepository.findRestaurantInformation(restaurant);
        if (restaurantInfo.isEmpty()){
            log.warn("Could not find the restaurantInfo with restaurantInfo {}, ", restaurantInfo);
            throw new OrderDomainException("Could not find the restaurantInfo with restaurantInfo {}"+ createOrderCommand.getRestaurantId());
        }
      return  restaurantInfo.get();
    }

    private void checkCustomer(UUID customerId) {
       var customer = customerRepository.findCustomer(customerId);
       if (customer.isEmpty()){
           log.warn("Could not find the customer with customer id {}, ", customerId);
           throw new OrderDomainException(String.format("Could not find the customer with customer id = %s ", customerId));
       }
    }

    private Order saveOrder(Order order){
       var orderResult =  orderRepository.save(order);
       if ( orderResult == null){
           log.error("Could not create save order");
           throw new OrderDomainException("Could not save order");
       }
       log.info("order with save id {}, ", order.getId().getValue());
       return orderResult;
    }
}
