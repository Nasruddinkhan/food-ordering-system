package in.mypractice.food.ordering.system.domain.ports.input.message.listener.payment;

import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.event.OrderPaidEvent;
import in.mypractice.food.ordering.system.domain.dto.message.PaymentResponse;
import in.mypractice.food.ordering.system.domain.saga.OrderPaymentSaga;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

    private final OrderPaymentSaga orderPaymentSaga;

    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {
        OrderPaidEvent orderPaidEvent = orderPaymentSaga.process(paymentResponse);
        log.info("Publishing order event for order id : {}", paymentResponse.getOrderId());
        orderPaidEvent.fire();

    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {
        orderPaymentSaga.rollback(paymentResponse);
        log.info("order is rollback event for order id : {} with failure message",
                paymentResponse.getOrderId(), String.join(Order.FAILURE_MESSAGE_DELIMITER, paymentResponse.getFailureMessages()));
    }
}
