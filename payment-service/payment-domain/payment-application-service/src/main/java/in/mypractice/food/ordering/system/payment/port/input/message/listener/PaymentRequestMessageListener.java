package in.mypractice.food.ordering.system.payment.port.input.message.listener;

import in.mypractice.food.ordering.system.payment.dto.PaymentRequest;

public interface PaymentRequestMessageListener {
    void completePayment(PaymentRequest paymentRequest);
    void cancelPayment(PaymentRequest paymentRequest);
}
