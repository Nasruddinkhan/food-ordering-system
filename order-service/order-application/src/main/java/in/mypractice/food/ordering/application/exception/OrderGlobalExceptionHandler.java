package in.mypractice.food.ordering.application.exception;

import in.mypractice.food.ordering.service.exception.OrderDomainException;
import in.mypractice.food.ordering.service.exception.OrderNotFoundException;
import in.mypractice.food.ordering.system.order.service.dataaccess.ErrorDto;
import in.mypractice.food.ordering.system.order.service.dataaccess.GlobalHandlerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class OrderGlobalExceptionHandler extends GlobalHandlerException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderDomainException.class)
    public ErrorDto handleOrderDomainException(OrderDomainException orderDomainException){
        log.error(orderDomainException.getMessage(), orderDomainException);
        return new ErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(),orderDomainException.getMessage() );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ErrorDto handleOrderNotFoundException(OrderNotFoundException orderDomainException){
        log.error(orderDomainException.getMessage(), orderDomainException);
        return new ErrorDto(HttpStatus.NOT_FOUND.getReasonPhrase(),orderDomainException.getMessage() );
    }
}
