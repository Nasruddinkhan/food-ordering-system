package in.mypractice.food.ordering.system.domain.ports.output.repository;

import in.mypractice.food.ordering.service.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
 Optional<Customer> findCustomer(UUID customerUuidId);

}
