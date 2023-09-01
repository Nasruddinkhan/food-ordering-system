package in.mypractice.food.ordering.system.payment.messaging.kafka.publisher;

import in.mypractice.food.ordering.system.kafka.producer.KafkaMessageHelper;
import in.mypractice.food.ordering.system.kafka.producer.service.KafkaProducer;
import in.mypractice.food.ordering.system.order.avro.model.PaymentRequestAvroModel;
import in.mypractice.food.ordering.system.order.avro.model.PaymentResponseAvroModel;
import in.mypractice.food.ordering.system.payment.config.PaymentServiceConfigData;
import in.mypractice.food.ordering.system.payment.messaging.mapper.PaymentMessagingDataMapper;
import in.mypractice.food.ordering.system.payment.port.output.message.publisher.PaymentCompleteMessagePublisher;
import in.mypractice.food.ordering.system.payment.service.domain.event.PaymentCompeteEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCompletedKafkaMessagePublisher implements PaymentCompleteMessagePublisher {

    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;
    private final PaymentServiceConfigData serviceConfigData;
    private final KafkaMessageHelper messageHelper;

    @Override
    public void publish(PaymentCompeteEvent domainEvent) {
        String orderId = domainEvent.getPayment().getOrderId().getValue().toString();
        log.info(String.format("Received payment order id : %s", orderId));
        try {
            PaymentResponseAvroModel paymentResponseAvroModel =
                    paymentMessagingDataMapper.paymentCompleteEventToPaymentAvroModel(domainEvent);

            kafkaProducer.send(serviceConfigData.getPaymentResponseTopicName(), orderId, paymentResponseAvroModel,
                    messageHelper.getKafkaCallBack(serviceConfigData.getPaymentResponseTopicName(), paymentResponseAvroModel, orderId, "PaymentResponseAvroModel"));
        } catch (Exception e) {
            log.error(String.format("Error while sending PaymentResponseAvroModel message to kafka with order id: %s error: %s ", orderId, e.getMessage()));
        }
    }
}
