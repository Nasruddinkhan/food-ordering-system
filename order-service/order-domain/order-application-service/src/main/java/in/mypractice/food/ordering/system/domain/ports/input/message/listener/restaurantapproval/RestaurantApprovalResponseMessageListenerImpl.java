package in.mypractice.food.ordering.system.domain.ports.input.message.listener.restaurantapproval;

import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.event.OrderCancelledEvent;
import in.mypractice.food.ordering.system.domain.dto.message.RestaurantApprovalResponse;
import in.mypractice.food.ordering.system.domain.saga.OrderApprovalSaga;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener{
    private final OrderApprovalSaga orderApprovalSaga;
    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {
        orderApprovalSaga.process(restaurantApprovalResponse);
        log.info("Order is approved for order id: {}", restaurantApprovalResponse.getOrderId());

    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {
     OrderCancelledEvent orderCancelledEvent = orderApprovalSaga.rollback(restaurantApprovalResponse);
     log.info("Publishing order cancelled event for order id : {} with ", restaurantApprovalResponse.getOrderId(),
             String.join(Order.FAILURE_MESSAGE_DELIMITER, restaurantApprovalResponse.getFailureMessages()));
      orderCancelledEvent.fire();
    }
}
