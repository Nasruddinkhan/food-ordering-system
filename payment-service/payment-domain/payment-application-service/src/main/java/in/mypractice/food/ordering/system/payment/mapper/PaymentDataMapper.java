package in.mypractice.food.ordering.system.payment.mapper;

import in.mypractice.food.ordering.system.domain.valueobject.CustomerId;
import in.mypractice.food.ordering.system.domain.valueobject.Money;
import in.mypractice.food.ordering.system.domain.valueobject.OrderId;
import in.mypractice.food.ordering.system.payment.dto.PaymentRequest;
import in.mypractice.food.ordering.system.payment.service.domain.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {
    public Payment paymentRequestModelToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .orderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .customerId(new CustomerId(UUID.fromString(paymentRequest.getCustomerId())))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }
}
