package in.mypractice.food.ordering.system.order.service.dataaccess.customer.mapper;

import in.mypractice.food.ordering.domain.valueobject.CustomerId;
import in.mypractice.food.ordering.service.entity.Customer;
import in.mypractice.food.ordering.system.order.service.dataaccess.customer.entity.CustomerEntity;

public class CustomerDataMapper {
    public Customer customerEntityToCustomer(CustomerEntity customer){
    return new Customer(new CustomerId(customer.getId()));
    }
}
