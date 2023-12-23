package in.mypractice.food.ordering.system.order.service.dataaccess;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalHandlerException {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class})
    public ErrorDto handleInternalException(Exception exception){
        log.error(exception.getMessage(), exception);
        return new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),"Unexpected Error!" );
    }


    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleValidationException(ValidationException validationException){
        if (validationException instanceof ConstraintViolationException constraintViolationException){
            String violations = extractViolationException(constraintViolationException);
            return new ErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(),violations);
        }
        return new ErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(),validationException.getMessage());
    }

    private String extractViolationException(ConstraintViolationException constraintViolationException) {
        return constraintViolationException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("~"));

    }
}
