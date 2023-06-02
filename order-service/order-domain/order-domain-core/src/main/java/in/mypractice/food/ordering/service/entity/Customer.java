package in.mypractice.food.ordering.service.entity;

import in.mypractice.food.ordering.system.domain.entity.AggregateRoot;
import in.mypractice.food.ordering.system.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    public Customer(){}

    public Customer(CustomerId customerId){
        super.setId(customerId);
    }
}
