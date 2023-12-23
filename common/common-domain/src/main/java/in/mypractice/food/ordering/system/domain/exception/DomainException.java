package in.mypractice.food.ordering.system.domain.exception;

public class DomainException extends RuntimeException{
    public DomainException(String msg){
       super(msg);
    }
    public DomainException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
