package in.mypractice.food.ordering.system.payment.dataaccess.credithistory.adapter;

import in.mypractice.food.ordering.system.domain.valueobject.CustomerId;
import in.mypractice.food.ordering.system.payment.dataaccess.credithistory.entity.CreditHistoryEntity;
import in.mypractice.food.ordering.system.payment.dataaccess.credithistory.mapper.CreditHistoryDataAccessMapper;
import in.mypractice.food.ordering.system.payment.dataaccess.credithistory.repository.CreditHistoryJpaRepository;
import in.mypractice.food.ordering.system.payment.port.output.repository.CreditEntryHistoryRepository;
import in.mypractice.food.ordering.system.payment.service.domain.entity.CreditHistory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CreditHistoryRepositoryImpl implements CreditEntryHistoryRepository {

    private final CreditHistoryJpaRepository creditHistoryJpaRepository;
    private final CreditHistoryDataAccessMapper creditHistoryDataAccessMapper;

    public CreditHistoryRepositoryImpl(CreditHistoryJpaRepository creditHistoryJpaRepository,
                                       CreditHistoryDataAccessMapper creditHistoryDataAccessMapper) {
        this.creditHistoryJpaRepository = creditHistoryJpaRepository;
        this.creditHistoryDataAccessMapper = creditHistoryDataAccessMapper;
    }

    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        return creditHistoryDataAccessMapper.creditHistoryEntityToCreditHistory(creditHistoryJpaRepository
                .save(creditHistoryDataAccessMapper.creditHistoryToCreditHistoryEntity(creditHistory)));
    }

    @Override
    public Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId) {
        Optional<List<CreditHistoryEntity>> creditHistory =
                creditHistoryJpaRepository.findByCustomerId(customerId.getValue());
        return creditHistory
                .map(creditHistoryList ->
                        creditHistoryList.stream()
                                .map(creditHistoryDataAccessMapper::creditHistoryEntityToCreditHistory)
                                .collect(Collectors.toList()));
    }
}
