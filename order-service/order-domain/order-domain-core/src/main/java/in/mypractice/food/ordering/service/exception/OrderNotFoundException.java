package in.mypractice.food.ordering.service.exception;

import in.mypractice.food.ordering.system.domain.exception.DomainException;

public class OrderNotFoundException extends DomainException {
    public OrderNotFoundException(String msg) {
        super(msg);
    }

    public OrderNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
