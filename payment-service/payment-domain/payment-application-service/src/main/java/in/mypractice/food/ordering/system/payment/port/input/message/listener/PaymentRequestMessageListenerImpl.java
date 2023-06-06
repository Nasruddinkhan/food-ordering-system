package in.mypractice.food.ordering.system.payment.port.input.message.listener;

import in.mypractice.food.ordering.system.payment.dto.PaymentRequest;
import in.mypractice.food.ordering.system.payment.port.input.message.PaymentRequestMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {
    @Override
    public void completePayment(PaymentRequest paymentRequest) {

    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {

    }
}
