package in.mypractice.food.ordering.system.payment.messaging.mapper;

import in.mypractice.food.ordering.system.domain.valueobject.PaymentOrderStatus;
import in.mypractice.food.ordering.system.order.avro.model.PaymentRequestAvroModel;
import in.mypractice.food.ordering.system.order.avro.model.PaymentResponseAvroModel;
import in.mypractice.food.ordering.system.order.avro.model.PaymentStatus;
import in.mypractice.food.ordering.system.payment.dto.PaymentRequest;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentCompetedEvent;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMessagingDataMapper {
    public PaymentResponseAvroModel paymentCompleteEventToPaymentAvroModel(PaymentCompetedEvent paymentCompeteEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(paymentCompeteEvent.getPayment().getCustomerId().getValue().toString())
                .setPaymentId(paymentCompeteEvent.getPayment().getId().getValue().toString())
                .setOrderId(paymentCompeteEvent.getPayment().getOrderId().getValue().toString())
                .setPrice(paymentCompeteEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentCompeteEvent.getPayment().getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentCompeteEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentCompeteEvent.getFailureMessages())
                .build();

    }


    public PaymentResponseAvroModel paymentCancelEventToPaymentAvroModel(PaymentCancelledEvent paymentCancelledEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(paymentCancelledEvent.getPayment().getCustomerId().getValue().toString())
                .setPaymentId(paymentCancelledEvent.getPayment().getId().getValue().toString())
                .setPrice(paymentCancelledEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(paymentCancelledEvent.getPayment().getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(paymentCancelledEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(paymentCancelledEvent.getFailureMessages())
                .build();

    }

    public PaymentResponseAvroModel paymentFailedEventToPaymentAvroModel(PaymentFailedEvent failedEvent) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId("")
                .setCustomerId(failedEvent.getPayment().getCustomerId().getValue().toString())
                .setPaymentId(failedEvent.getPayment().getId().getValue().toString())
                .setPrice(failedEvent.getPayment().getPrice().getAmount())
                .setCreatedAt(failedEvent.getPayment().getCreatedAt().toInstant())
                .setPaymentStatus(PaymentStatus.valueOf(failedEvent.getPayment().getPaymentStatus().name()))
                .setFailureMessages(failedEvent.getFailureMessages())
                .build();
    }

    public PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel) {
        return PaymentRequest.builder()
                .id(paymentRequestAvroModel.getId())
                .sagaId(paymentRequestAvroModel.getSagaId())
                .customerId(paymentRequestAvroModel.getCustomerId())
                .orderId(paymentRequestAvroModel.getOrderId())
                .price(paymentRequestAvroModel.getPrice())
                .createdAt(paymentRequestAvroModel.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.valueOf(paymentRequestAvroModel.getPaymentOrderStatus().name()))
                .build();
    }
}
