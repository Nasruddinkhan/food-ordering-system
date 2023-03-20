package in.mypractice.food.ordering.domain.exception;

public class DomainException extends RuntimeException{
    public DomainException(String msg){
       super(msg);
    }
    public DomainException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
