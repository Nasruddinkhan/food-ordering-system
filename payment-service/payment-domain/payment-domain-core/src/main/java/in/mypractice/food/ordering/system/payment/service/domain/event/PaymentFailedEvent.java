package in.mypractice.food.ordering.system.payment.service.domain.event;

import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentFailedEvent extends PaymentEvent{
    public PaymentFailedEvent(Payment payment, List<String> failureMessages, ZonedDateTime createdAt) {
        super(payment, failureMessages, createdAt);
    }
}
