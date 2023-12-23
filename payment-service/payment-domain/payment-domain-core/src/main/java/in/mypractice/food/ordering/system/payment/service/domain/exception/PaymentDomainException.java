package in.mypractice.food.ordering.system.payment.service.domain.exception;

import in.mypractice.food.ordering.system.domain.exception.DomainException;

public class PaymentDomainException extends DomainException {
    public PaymentDomainException(String msg) {
        super(msg);
    }

    public PaymentDomainException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
