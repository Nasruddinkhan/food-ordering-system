package in.mypractice.food.ordering.system.payment.messaging.kafka.listener;

import in.mypractice.food.ordering.system.kafka.consumer.KafkaConsumer;
import in.mypractice.food.ordering.system.order.avro.model.PaymentOrderStatus;
import in.mypractice.food.ordering.system.order.avro.model.PaymentRequestAvroModel;
import in.mypractice.food.ordering.system.payment.messaging.mapper.PaymentMessagingDataMapper;
import in.mypractice.food.ordering.system.payment.port.input.message.PaymentRequestMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaymentRequestKafkaListener implements KafkaConsumer<PaymentRequestAvroModel> {

    private final PaymentRequestMessageListener requestMessageListener;
    private final PaymentMessagingDataMapper messagingDataMapper;

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${order-service.payment-request-topic-name}")
    public void receive(@Payload List<PaymentRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info(String.format("%s number of payment request with key %s, partitions %s offset %s",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString()
        ));
        messages.forEach(requestAvroModel -> {
            if (PaymentOrderStatus.PENDING == requestAvroModel.getPaymentOrderStatus()) {
                log.info(String.format("Processing payment for order id : %s ", requestAvroModel.getOrderId()));
                requestMessageListener.completePayment(messagingDataMapper.paymentRequestAvroModelToPaymentRequest(requestAvroModel));
            } else if (PaymentOrderStatus.CANCELLED == requestAvroModel.getPaymentOrderStatus()) {
                log.info(String.format("Cancelling payment for order id : %s ", requestAvroModel.getOrderId()));
                requestMessageListener.cancelPayment(messagingDataMapper
                        .paymentRequestAvroModelToPaymentRequest(requestAvroModel));
            }
        });
    }
}
