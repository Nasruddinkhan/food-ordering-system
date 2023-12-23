package in.mypractice.food.ordering.system.domain.saga;

import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.event.OrderPaidEvent;
import in.mypractice.food.ordering.service.service.OrderDomainService;
import in.mypractice.food.ordering.system.domain.dto.message.PaymentResponse;
import in.mypractice.food.ordering.system.domain.events.EmptyEvent;
import in.mypractice.food.ordering.system.domain.helper.OrderSagaHelper;
import in.mypractice.food.ordering.system.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import in.mypractice.food.ordering.system.saga.SagaStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentSaga implements SagaStep<PaymentResponse, OrderPaidEvent, EmptyEvent> {

    private final OrderDomainService domainService;
    private final OrderSagaHelper helper;
    private final OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher;

    @Transactional
    @Override
    public OrderPaidEvent process(PaymentResponse paymentResponse) {
        log.info("completing payment for order wit id : {}", paymentResponse.getOrderId());
        Order order = helper.findOrder(paymentResponse.getOrderId());
        OrderPaidEvent orderPaidEvent = domainService.payOrder(order, orderPaidRestaurantRequestMessagePublisher);
        helper.saveOrder(order);
        return orderPaidEvent;
    }
    @Transactional
    @Override
    public EmptyEvent rollback(PaymentResponse paymentResponse) {
        log.info("cancelling order with id : {}", paymentResponse.getOrderId());
        Order order = helper.findOrder(paymentResponse.getOrderId());
        domainService.cancelOrder(order, paymentResponse.getFailureMessages());
        helper.saveOrder(order);
        log.info("Order with id: {} is cancelled ", order.getId().getValue());
        return EmptyEvent.INSTANCE;
    }
}
