package in.mypractice.food.ordering.system.payment.service.domain.event;

import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentCancelledEvent extends PaymentEvent{
    public PaymentCancelledEvent(Payment payment, ZonedDateTime createdAt) {
        super(payment, List.of(), createdAt);
    }
}
