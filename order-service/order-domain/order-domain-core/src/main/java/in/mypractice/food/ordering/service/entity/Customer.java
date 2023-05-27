package in.mypractice.food.ordering.service.entity;

import in.mypractice.food.ordering.domain.entity.AggregateRoot;
import in.mypractice.food.ordering.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    public Customer(){}

    public Customer(CustomerId customerId){
        super.setId(customerId);
    }
}
