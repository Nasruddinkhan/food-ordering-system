package in.mypractice.food.ordering.system.payment.port.input.message.listener;

import in.mypractice.food.ordering.system.payment.dto.PaymentRequest;
import in.mypractice.food.ordering.system.payment.helper.PaymentRequestHelper;
import in.mypractice.food.ordering.system.payment.port.input.message.PaymentRequestMessageListener;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final PaymentRequestHelper paymentRequestHelper;


    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = paymentRequestHelper.persistPayment(paymentRequest);
        fireEvent(paymentEvent);
    }

    private void fireEvent(PaymentEvent paymentEvent) {
        log.info(String.format("Publishing payment event with payment id : %s and order id : %s",
                paymentEvent.getPayment().getId().getValue(), paymentEvent.getPayment().getOrderId().getValue()));
        paymentEvent.fire();
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = paymentRequestHelper.persistCancelPayment(paymentRequest);
        fireEvent(paymentEvent);
    }
}
