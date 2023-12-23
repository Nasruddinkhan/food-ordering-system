package in.mypractice.food.ordering.system.payment.service.domain.event;

import in.mypractice.food.ordering.system.domain.events.DomainEvent;
import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class PaymentEvent  implements DomainEvent<Payment> {
    private final Payment payment;
    private final List<String> failureMessages;
    private final ZonedDateTime createdAt;


    protected PaymentEvent(Payment payment, List<String> failureMessages, ZonedDateTime createdAt) {
        this.payment = payment;
        this.failureMessages = failureMessages;
        this.createdAt = createdAt;
    }

    public Payment getPayment() {
        return payment;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
