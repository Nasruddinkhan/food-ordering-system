package in.mypractice.food.ordering.service.exception;

import in.mypractice.food.ordering.system.domain.exception.DomainException;

public class OrderDomainException  extends DomainException {
    public OrderDomainException(String msg) {
        super(msg);
    }

    public OrderDomainException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
