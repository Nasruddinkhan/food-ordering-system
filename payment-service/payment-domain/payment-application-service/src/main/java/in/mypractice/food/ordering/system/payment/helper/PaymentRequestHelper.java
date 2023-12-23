package in.mypractice.food.ordering.system.payment.helper;

import in.mypractice.food.ordering.system.domain.valueobject.CustomerId;
import in.mypractice.food.ordering.system.payment.dto.PaymentRequest;
import in.mypractice.food.ordering.system.payment.exception.PaymentApplicationServiceException;
import in.mypractice.food.ordering.system.payment.mapper.PaymentDataMapper;
import in.mypractice.food.ordering.system.payment.port.output.message.publisher.PaymentCancelledMessagePublisher;
import in.mypractice.food.ordering.system.payment.port.output.message.publisher.PaymentCompleteMessagePublisher;
import in.mypractice.food.ordering.system.payment.port.output.message.publisher.PaymentFailedMessagePublisher;
import in.mypractice.food.ordering.system.payment.port.output.repository.CreditEntryHistoryRepository;
import in.mypractice.food.ordering.system.payment.port.output.repository.CreditEntryRepository;
import in.mypractice.food.ordering.system.payment.port.output.repository.PaymentRepository;
import in.mypractice.food.ordering.system.payment.service.domain.entity.CreditEntry;
import in.mypractice.food.ordering.system.payment.service.domain.entity.CreditHistory;
import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentEvent;
import in.mypractice.food.ordering.system.payment.service.domain.service.PaymentDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRequestHelper {
    private final PaymentDomainService paymentDomainService;
    private final PaymentDataMapper paymentDataMapper;
    private final PaymentRepository paymentRepository;
    private final CreditEntryRepository creditEntryRepository;
    private final CreditEntryHistoryRepository creditEntryHistoryRepository;
    private final PaymentCompleteMessagePublisher paymentCompleteMessagePublisher;
    private final PaymentCancelledMessagePublisher cancelledMessagePublisher;
    private final PaymentFailedMessagePublisher failedMessagePublisher;
    @Transactional
    public PaymentEvent persistPayment(PaymentRequest paymentRequest) {
        log.info("Received payment complete event for order id: {}", paymentRequest.getOrderId());
        Payment payment = paymentDataMapper.paymentRequestModelToPayment(paymentRequest);
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<String> failureMessage = new ArrayList<>();
        List<CreditHistory> creditHistories = getCreditHistory(payment.getCustomerId());
        PaymentEvent paymentEvent = paymentDomainService.validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessage, paymentCompleteMessagePublisher, failedMessagePublisher);
        persistDBObject(payment, failureMessage, creditEntry, creditHistories);
        return paymentEvent;
    }

    private void persistDBObject(Payment payment, List<String> failureMessage, CreditEntry creditEntry, List<CreditHistory> creditHistories) {
        paymentRepository.save(payment);
        if (failureMessage.isEmpty()) {
            creditEntryRepository.save(creditEntry);
            creditEntryHistoryRepository.save(creditHistories.get(creditHistories.size() - 1));
        }
    }


    public PaymentEvent persistCancelPayment(PaymentRequest paymentRequest) {
        log.info("Received payment cancel event for order id : {}", paymentRequest.getOrderId());
        Optional<Payment> paymentResponse = paymentRepository.findByOrderId(UUID.fromString(paymentRequest.getOrderId()));
        if (paymentResponse.isEmpty()) {
            log.error("payment with order id: {} could not be found ", paymentRequest.getOrderId());
            throw new PaymentApplicationServiceException(String.format("Payment with order id %s could not be found ", paymentRequest.getOrderId()));

        }
        Payment payment = paymentResponse.get();
        CreditEntry creditEntry = getCreditEntry(payment.getCustomerId());
        List<CreditHistory> creditHistories = getCreditHistory(payment.getCustomerId());
        List<String> failureMessage = new ArrayList<>();
        PaymentEvent paymentEvent = paymentDomainService.validateAndCancelPayment(payment, creditEntry, creditHistories, failureMessage, cancelledMessagePublisher, failedMessagePublisher);
        persistDBObject(payment, failureMessage, creditEntry, creditHistories);
        return paymentEvent;

    }

    private List<CreditHistory> getCreditHistory(CustomerId customerId) {
        Optional<List<CreditHistory>> creditHistories = creditEntryHistoryRepository.findByCustomerId(customerId);
        if (creditHistories.isEmpty()) {
            throw new PaymentApplicationServiceException("could not find credit history for this customer : " + customerId.getValue());
        }
        return creditHistories.get();
    }

    private CreditEntry getCreditEntry(CustomerId customerId) {
        Optional<CreditEntry> creditEntry = creditEntryRepository.findByCustomerId(customerId);
        if (creditEntry.isEmpty()) {
            throw new PaymentApplicationServiceException("could not find credit entry for this customer : " + customerId.getValue());
        }
        return creditEntry.get();
    }
}
