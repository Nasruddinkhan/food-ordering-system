package in.mypractice.food.ordering.system.payment.dataaccess.payment.mapper;

import in.mypractice.food.ordering.system.domain.valueobject.CustomerId;
import in.mypractice.food.ordering.system.domain.valueobject.Money;
import in.mypractice.food.ordering.system.domain.valueobject.OrderId;
import in.mypractice.food.ordering.system.payment.dataaccess.payment.entity.PaymentEntity;
import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;
import in.mypractice.food.ordering.system.payment.service.domain.valueobject.PaymentId;
import org.springframework.stereotype.Component;

@Component
public class PaymentDataAccessMapper {

    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId().getValue())
                .customerId(payment.getCustomerId().getValue())
                .orderId(payment.getOrderId().getValue())
                .price(payment.getPrice().getAmount())
                .status(payment.getPaymentStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        return Payment.builder()
                .paymentId(new PaymentId(paymentEntity.getId()))
                .customerId(new CustomerId(paymentEntity.getCustomerId()))
                .orderId(new OrderId(paymentEntity.getOrderId()))
                .price(new Money(paymentEntity.getPrice()))
                .createdAt(paymentEntity.getCreatedAt())
                .build();
    }

}
