package in.mypractice.food.ordering.system.payment.service.domain.exception;

import in.mypractice.food.ordering.system.domain.exception.DomainException;

public class PaymentNotFound extends DomainException {
    public PaymentNotFound(String msg) {
        super(msg);
    }

    public PaymentNotFound(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
