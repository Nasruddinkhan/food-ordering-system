package in.mypractice.food.ordering.system.payment.service.domain.service.impl;

import in.mypractice.food.ordering.system.domain.events.publisher.DomainEventPublisher;
import in.mypractice.food.ordering.system.domain.valueobject.Money;
import in.mypractice.food.ordering.system.domain.valueobject.PaymentStatus;
import in.mypractice.food.ordering.system.payment.service.domain.entity.CreditEntry;
import in.mypractice.food.ordering.system.payment.service.domain.entity.CreditHistory;
import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentCompeteEvent;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentEvent;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;
import in.mypractice.food.ordering.system.payment.service.domain.service.PaymentDomainService;
import in.mypractice.food.ordering.system.payment.service.domain.valueobject.CreditHistoryId;
import in.mypractice.food.ordering.system.payment.service.domain.valueobject.TransactionType;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static in.mypractice.food.ordering.system.domain.constants.DomainConstants.UTC;

@Slf4j
public class PaymentDomainServiceImpl implements PaymentDomainService {
    @Override
    public PaymentEvent validateAndInitiatePayment(Payment payment,
                                                   CreditEntry creditEntry,
                                                   List<CreditHistory> creditHistories,
                                                   List<String> failureMessages,
                                                   DomainEventPublisher<PaymentCompeteEvent> eventPublisher,
                                                   DomainEventPublisher<PaymentFailedEvent> eventFailedPublisher) {
        payment.validatePayment(failureMessages);
        payment.initializationPayment();
        validateCreditEntry(payment, creditEntry, failureMessages);
        subtractCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.DEBIT);
        validateCreditHistory(creditEntry, creditHistories, failureMessages);
        if (failureMessages.isEmpty()) {
            log.error("Payment is initiated for order id: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.COMPLETED);
            return new PaymentCompeteEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), eventPublisher);
        } else {
            log.error("Payment initiation is failed for order id: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, failureMessages, ZonedDateTime.now(ZoneId.of(UTC)), eventFailedPublisher);
        }
    }


    @Override
    public PaymentEvent validateAndCancelPayment(Payment payment,
                                                 CreditEntry creditEntry,
                                                 List<CreditHistory> creditHistories,
                                                 List<String> failureMessages,
                                                 DomainEventPublisher<PaymentCancelledEvent> eventPublisher,
                                                 DomainEventPublisher<PaymentFailedEvent> failEventPublisher
    ) {
        payment.validatePayment(failureMessages);
        addCreditEntry(payment, creditEntry);
        updateCreditHistory(payment, creditHistories, TransactionType.CREDIT);
        if (failureMessages.isEmpty()) {
            log.info("Payment is cancelled for order id :{}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.CANCELLED);
            return new PaymentCancelledEvent(payment, ZonedDateTime.now(ZoneId.of(UTC)), eventPublisher);
        } else {
            log.error("Payment cancelled is failed for order id: {}", payment.getOrderId().getValue());
            payment.updateStatus(PaymentStatus.FAILED);
            return new PaymentFailedEvent(payment, failureMessages, ZonedDateTime.now(ZoneId.of(UTC)), failEventPublisher);

        }
    }

    private void addCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.addCreditAMount(payment.getPrice());
    }

    private void validateCreditHistory(CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages) {
        Money totalCreditHistory = getTotalHistoryAmount(creditHistories, TransactionType.CREDIT);
        Money totalDebitHistory = getTotalHistoryAmount(creditHistories, TransactionType.DEBIT);
        if (totalDebitHistory.isGreaterThan(totalCreditHistory)) {
            log.error("Customer with id: {} doesn't have enough credit for payment", creditEntry.getCustomerId().getValue());
            failureMessages.add("Customer with id = " + creditEntry.getCustomerId().getValue() + " doesn't have enough credit balance");
        }

        if (!creditEntry.getTotalCreditAmount().equals(totalCreditHistory.subtract(totalDebitHistory))) {
            log.error("Customer history total is not equal to current credit for customer Id: {}!", creditEntry.getCustomerId().getValue());
            failureMessages.add(String.format("Customer history total is not equal to current credit for customer Id: %s!", creditEntry.getCustomerId().getValue()));
        }
    }

    private Money getTotalHistoryAmount(List<CreditHistory> creditHistories, TransactionType transactionType) {

        return creditHistories.stream().filter(creditHistory -> transactionType == creditHistory.getTransactionType())
                .map(CreditHistory::getAmount)
                .reduce(Money.ZERO, Money::add);
    }

    private void updateCreditHistory(Payment payment, List<CreditHistory> creditHistories, TransactionType transactionType) {
        creditHistories.add(CreditHistory.builder()
                .creditHistoryId(new CreditHistoryId(UUID.randomUUID()))
                .customerId(payment.getCustomerId())
                .amount(payment.getPrice())
                .transactionType(transactionType)
                .build());
    }

    private void subtractCreditEntry(Payment payment, CreditEntry creditEntry) {
        creditEntry.subtractCreditAMount(payment.getPrice());
    }

    private void validateCreditEntry(Payment payment, CreditEntry creditEntry, List<String> failureMessages) {
        if (payment.getPrice().isGreaterThan(creditEntry.getTotalCreditAmount())) {
            log.error("Customer with id: {} doesn't have enough credit for payment", payment.getCustomerId().getValue());
            failureMessages.add("Customer with id = " + payment.getCustomerId().getValue() + " doesn't have enough credit balance");

        }
    }
}
