package in.mypractice.food.ordering.system.payment.dataaccess.creditentry.adapter;


import in.mypractice.food.ordering.system.domain.valueobject.CustomerId;
import in.mypractice.food.ordering.system.payment.dataaccess.creditentry.mapper.CreditEntryDataAccessMapper;
import in.mypractice.food.ordering.system.payment.dataaccess.creditentry.repository.CreditEntryJpaRepository;
import in.mypractice.food.ordering.system.payment.port.output.repository.CreditEntryRepository;
import in.mypractice.food.ordering.system.payment.service.domain.entity.CreditEntry;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreditEntryRepositoryImpl implements CreditEntryRepository {

    private final CreditEntryJpaRepository creditEntryJpaRepository;
    private final CreditEntryDataAccessMapper creditEntryDataAccessMapper;

    public CreditEntryRepositoryImpl(CreditEntryJpaRepository creditEntryJpaRepository,
                                     CreditEntryDataAccessMapper creditEntryDataAccessMapper) {
        this.creditEntryJpaRepository = creditEntryJpaRepository;
        this.creditEntryDataAccessMapper = creditEntryDataAccessMapper;
    }

    @Override
    public CreditEntry save(CreditEntry creditEntry) {
        return creditEntryDataAccessMapper
                .creditEntryEntityToCreditEntry(creditEntryJpaRepository
                        .save(creditEntryDataAccessMapper.creditEntryToCreditEntryEntity(creditEntry)));
    }

    @Override
    public Optional<CreditEntry> findByCustomerId(CustomerId customerId) {
        return creditEntryJpaRepository
                .findByCustomerId(customerId.getValue())
                .map(creditEntryDataAccessMapper::creditEntryEntityToCreditEntry);
    }
}
