package in.mypractice.food.ordering.domain.ports.input.message.listener.payment;

import in.mypractice.food.ordering.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
