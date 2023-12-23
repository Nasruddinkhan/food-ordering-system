package in.mypractice.food.ordering.system.domain.saga;

import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.event.OrderCancelledEvent;
import in.mypractice.food.ordering.service.service.OrderDomainService;
import in.mypractice.food.ordering.system.domain.dto.message.RestaurantApprovalResponse;
import in.mypractice.food.ordering.system.domain.events.EmptyEvent;
import in.mypractice.food.ordering.system.domain.helper.OrderSagaHelper;
import in.mypractice.food.ordering.system.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import in.mypractice.food.ordering.system.domain.ports.output.repository.OrderRepository;
import in.mypractice.food.ordering.system.saga.SagaStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderApprovalSaga implements SagaStep<RestaurantApprovalResponse, EmptyEvent, OrderCancelledEvent> {
    private final OrderDomainService domainService;
    private final OrderSagaHelper sagaHelper;
    private final OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher;

    @Transactional
    @Override
    public EmptyEvent process(RestaurantApprovalResponse restaurantApprovalResponse) {
        log.info("Approving order with id : {}", restaurantApprovalResponse.getOrderId());
        Order order = sagaHelper.findOrder(restaurantApprovalResponse.getOrderId());
        domainService.approveOrder(order);
        sagaHelper.saveOrder(order);
        log.info("Order with id: {} is approved ", order.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public OrderCancelledEvent rollback(RestaurantApprovalResponse restaurantApprovalResponse) {
        log.info("Cancelling order with id : {}", restaurantApprovalResponse.getOrderId());
        Order order = sagaHelper.findOrder(restaurantApprovalResponse.getOrderId());
        OrderCancelledEvent orderCancelledEvent = domainService.cancelOrderPayment(order, restaurantApprovalResponse.getFailureMessages(), orderCancelledPaymentRequestMessagePublisher);
        sagaHelper.saveOrder(order);
        log.info("Order with id : {} is cancelling ", restaurantApprovalResponse.getOrderId());
        return orderCancelledEvent;
    }
}
