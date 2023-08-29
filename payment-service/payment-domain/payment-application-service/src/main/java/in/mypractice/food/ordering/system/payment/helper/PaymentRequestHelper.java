package in.mypractice.food.ordering.system.payment.helper;

import in.mypractice.food.ordering.system.domain.valueobject.CustomerId;
import in.mypractice.food.ordering.system.payment.dto.PaymentRequest;
import in.mypractice.food.ordering.system.payment.exception.PaymentApplicationServiceException;
import in.mypractice.food.ordering.system.payment.mapper.PaymentDataMapper;
import in.mypractice.food.ordering.system.payment.port.output.repository.CreditEntryHistoryRepository;
import in.mypractice.food.ordering.system.payment.port.output.repository.CreditEntryRepository;
import in.mypractice.food.ordering.system.payment.port.output.repository.PaymentRepository;
import in.mypractice.food.ordering.system.payment.service.domain.entity.CreditEntry;
import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentEvent;
import in.mypractice.food.ordering.system.payment.service.domain.service.PaymentDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRequestHelper {
    public final PaymentDomainService paymentDomainService;
    public final PaymentDataMapper paymentDataMapper;
    public final PaymentRepository paymentRepository;
    public final CreditEntryRepository creditEntryRepository;
    public final CreditEntryHistoryRepository creditEntryHistoryRepository;

    @Transactional
    public PaymentEvent persistPayment(PaymentRequest paymentRequest) {
        log.info("Received payment complete event for order id: {}", paymentRequest.getOrderId());
        Payment payment = paymentDataMapper.paymentRequestModelToPayment(paymentRequest);
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        return null;
    }

    private CreditEntry getCreditEntry(CustomerId customerId) {
        Optional<CreditEntry> creditEntry = creditEntryRepository.findByCustomerId(customerId);
        if (creditEntry.isEmpty()) {
            throw new PaymentApplicationServiceException("could not find credit entry for this customer : " + customerId.getValue());
        }
        return creditEntry.get();
    }
}
