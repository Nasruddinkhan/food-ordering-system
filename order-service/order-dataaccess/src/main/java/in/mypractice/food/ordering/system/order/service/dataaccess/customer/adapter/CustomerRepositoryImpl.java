package in.mypractice.food.ordering.system.order.service.dataaccess.customer.adapter;

import in.mypractice.food.ordering.domain.ports.output.repository.CustomerRepository;
import in.mypractice.food.ordering.service.entity.Customer;
import in.mypractice.food.ordering.system.order.service.dataaccess.customer.mapper.CustomerDataMapper;
import in.mypractice.food.ordering.system.order.service.dataaccess.customer.repository.CustomerJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {
    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataMapper customerDataMapper;

    public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository, CustomerDataMapper customerDataMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataMapper = customerDataMapper;
    }

    @Override
    public Optional<Customer> findCustomer(UUID uuid) {
        return customerJpaRepository.findById(uuid).map(customerDataMapper::customerEntityToCustomer);
    }
}
