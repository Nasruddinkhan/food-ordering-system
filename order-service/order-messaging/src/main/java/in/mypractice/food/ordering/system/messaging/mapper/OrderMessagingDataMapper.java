package in.mypractice.food.ordering.system.messaging.mapper;

import in.mypractice.food.ordering.domain.dto.message.PaymentResponse;
import in.mypractice.food.ordering.domain.dto.message.RestaurantApprovalResponse;
import in.mypractice.food.ordering.domain.valueobject.OrderApprovalStatus;
import in.mypractice.food.ordering.domain.valueobject.PaymentStatus;
import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.event.OrderCancelledEvent;
import in.mypractice.food.ordering.service.event.OrderCreatedEvent;
import in.mypractice.food.ordering.service.event.OrderPaidEvent;
import in.mypractice.foot.ordering.system.order.avro.model.*;

import java.util.UUID;

public class OrderMessagingDataMapper {
    public PaymentRequestAvroModel orderCreatedEventToPaymentRequestAvroModel(OrderCreatedEvent orderCreatedEvent) {
        Order order = orderCreatedEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setSagaId("")
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCreatedEvent.getCreateAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.PENDING)
                .build();
    }

    public PaymentRequestAvroModel orderCancelEventToPaymentRequestAvroModel(OrderCancelledEvent orderCancelledEvent) {
        Order order = orderCancelledEvent.getOrder();
        return PaymentRequestAvroModel.newBuilder()
                .setSagaId("")
                .setCustomerId(order.getCustomerId().getValue().toString())
                .setOrderId(order.getId().getValue().toString())
                .setPrice(order.getPrice().getAmount())
                .setCreatedAt(orderCancelledEvent.getCreateAt().toInstant())
                .setPaymentOrderStatus(PaymentOrderStatus.CANCELLED)
                .build();
    }

    public RestaurantApprovalRequestAvroModel orderPaidEventToRestaurantApprovalRequestAvroModel(OrderPaidEvent orderPaidEvent){
     Order order = orderPaidEvent.getOrder();
     return RestaurantApprovalRequestAvroModel.newBuilder()
             .setId(UUID.randomUUID().toString())
             .setSagaId("")
             .setOrderId(order.getId().getValue().toString())
             .setRestaurantId(order.getRestaurantId().getValue().toString())
             .setRestaurantOrderStatus(RestaurantOrderStatus.valueOf(order.getOrderStatus().name()))
             .setProducts(order.getItems().stream().map(orderItem -> Product.newBuilder()
                     .setId(orderItem.getProduct().getId().getValue().toString())
                     .setQuantity(orderItem.getQuantity())
                     .build()).toList())
             .setPrice(order.getPrice().getAmount())
             .setCreatedAt(orderPaidEvent.getCreateAt().toInstant())
             .setRestaurantOrderStatus(RestaurantOrderStatus.PAID)
             .build();
    }

    public PaymentResponse paymentResponseAvroModelToPaymentResponse(PaymentResponseAvroModel paymentResponseAvroModel){
        return PaymentResponse.builder()
                .id(paymentResponseAvroModel.getId())
                .sagaId(paymentResponseAvroModel.getSagaId())
                .paymentId(paymentResponseAvroModel.getPaymentId())
                .customerId(paymentResponseAvroModel.getCustomerId())
                .orderId(paymentResponseAvroModel.getOrderId())
                .price(paymentResponseAvroModel.getPrice())
                .createdAt(paymentResponseAvroModel.getCreatedAt())
                .paymentStatus(PaymentStatus.valueOf(paymentResponseAvroModel.getPaymentStatus().name()))
                .failureMessages(paymentResponseAvroModel.getFailureMessages())
                .build();
    }

    public RestaurantApprovalResponse approvalResponseAvroModelToApprovalResponse(RestaurantApprovalResponseAvroModel responseAvroModel) {
        return RestaurantApprovalResponse.builder()
                .id(responseAvroModel.getId())
                .sagaId(responseAvroModel.getSagaId())
                .orderId(responseAvroModel.getOrderId())
                .createdAt(responseAvroModel.getCreatedAt())
                .restaurantId(responseAvroModel.getRestaurantId())
                .failureMessages(responseAvroModel.getFailureMessages())
                .orderApprovalStatus(OrderApprovalStatus.valueOf(responseAvroModel.getOrderApprovalStatus().name()))
                .build();
    }
}
