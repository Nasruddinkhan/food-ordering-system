package in.mypractice.food.ordering.system.messaging.publisher;

import in.mypractice.food.ordering.system.domain.config.OrderServiceConfigData;
import in.mypractice.food.ordering.system.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import in.mypractice.food.ordering.service.event.OrderCancelledEvent;
import in.mypractice.food.ordering.system.kafka.producer.KafkaMessageHelper;
import in.mypractice.food.ordering.system.messaging.mapper.OrderMessagingDataMapper;
import in.mypractice.food.ordering.system.kafka.producer.service.KafkaProducer;
import in.mypractice.food.ordering.system.order.avro.model.PaymentRequestAvroModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor

public class CancelOrderKafkaMessagePublisher implements OrderCancelledPaymentRequestMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper kafkaMessageHelper;
    @Override
    public void publish(OrderCancelledEvent cancelledEvent) {
        String orderId =  cancelledEvent.getOrder().getId().getValue().toString();
        log.info("Received OrderCancelledEvent for orderId : {]", orderId);
        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderCancelEventToPaymentRequestAvroModel(cancelledEvent);
            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(), orderId, paymentRequestAvroModel,
                    kafkaMessageHelper.getKafkaCallBack(orderServiceConfigData.getPaymentResponseTopicName(), paymentRequestAvroModel, paymentRequestAvroModel.getOrderId(), "PaymentRequestAvroModel"));
            log.info("PaymentRequestAvroModel sent to kafka for orderId : {]", paymentRequestAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("Error while sending PaymentRequestAvroModel message to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }

}
