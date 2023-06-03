package in.mypractice.food.ordering.system.payment.service.domain.service;

import in.mypractice.food.ordering.system.payment.service.domain.entity.CreditEntry;
import in.mypractice.food.ordering.system.payment.service.domain.entity.CreditHistory;
import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentEvent;

import java.util.List;

public interface PaymentDomainService {
    PaymentEvent validateAndInitiatePayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages);

    PaymentEvent validateAndCancelPayment(Payment payment, CreditEntry creditEntry, List<CreditHistory> creditHistories, List<String> failureMessages);
}
