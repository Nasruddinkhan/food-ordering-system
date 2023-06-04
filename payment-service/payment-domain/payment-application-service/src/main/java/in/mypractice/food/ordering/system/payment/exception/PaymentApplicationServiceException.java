package in.mypractice.food.ordering.system.payment.exception;

import in.mypractice.food.ordering.system.domain.exception.DomainException;

public class PaymentApplicationServiceException extends DomainException {
    public PaymentApplicationServiceException(String msg) {
        super(msg);
    }

    public PaymentApplicationServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
