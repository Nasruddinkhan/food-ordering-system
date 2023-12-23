package in.mypractice.food.ordering.system.domain.helper;

import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.exception.OrderNotFoundException;
import in.mypractice.food.ordering.system.domain.ports.output.repository.OrderRepository;
import in.mypractice.food.ordering.system.domain.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderSagaHelper {
    private final OrderRepository  orderRepository;
    public Order findOrder(String orderId) {
        Optional<Order> order = orderRepository.findById(new OrderId(UUID.fromString(orderId)));
        if (order.isEmpty()) {
            log.error("completing payment for order with id : {}", orderId);
            throw new OrderNotFoundException(String.format("order with id %s could not be found", orderId));
        }
        return order.get();
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);

    }
}
