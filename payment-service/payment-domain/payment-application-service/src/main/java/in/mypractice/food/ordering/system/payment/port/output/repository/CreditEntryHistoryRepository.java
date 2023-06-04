package in.mypractice.food.ordering.system.payment.port.output.repository;

import in.mypractice.food.ordering.system.domain.valueobject.CustomerId;
import in.mypractice.food.ordering.system.payment.service.domain.entity.CreditEntry;
import in.mypractice.food.ordering.system.payment.service.domain.entity.CreditHistory;

import java.util.List;
import java.util.Optional;

public interface CreditEntryHistoryRepository  {

    CreditHistory save(CreditHistory creditHistory);
    Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId);


}
