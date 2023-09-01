package in.mypractice.food.ordering.system.payment.dataaccess.payment.adapter;

import in.mypractice.food.ordering.system.payment.dataaccess.payment.mapper.PaymentDataAccessMapper;
import in.mypractice.food.ordering.system.payment.dataaccess.payment.repository.PaymentJpaRepository;
import in.mypractice.food.ordering.system.payment.port.output.repository.PaymentRepository;
import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;

    public PaymentRepositoryImpl(PaymentJpaRepository paymentJpaRepository,
                                 PaymentDataAccessMapper paymentDataAccessMapper) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.paymentDataAccessMapper = paymentDataAccessMapper;
    }

    @Override
    public Payment save(Payment payment) {
        return paymentDataAccessMapper
                .paymentEntityToPayment(paymentJpaRepository
                        .save(paymentDataAccessMapper.paymentToPaymentEntity(payment)));
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return paymentJpaRepository.findByOrderId(orderId)
                .map(paymentDataAccessMapper::paymentEntityToPayment);
    }
}
