package in.mypractice.food.ordering.system.messaging.publisher;

import in.mypractice.food.ordering.domain.config.OrderServiceConfigData;
import in.mypractice.food.ordering.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import in.mypractice.food.ordering.service.event.OrderCreatedEvent;
import in.mypractice.food.ordering.system.messaging.mapper.OrderMessagingDataMapper;
import in.mypractice.food.ordering.system.messaging.publisher.helper.OrderKafkaMessageHelper;
import in.mypractice.foot.ordering.system.kafka.producer.service.KafkaProducer;
import in.mypractice.foot.ordering.system.order.avro.model.PaymentRequestAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateOrderKafkaMessagePublisher implements OrderCreatedPaymentRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    public CreateOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper, OrderServiceConfigData orderServiceConfigData, KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer, OrderKafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderCreatedEvent domainEvent) {
       String orderId =  domainEvent.getOrder().getId().getValue().toString();
       log.info("Received OrderCreatedEvent for orderId : {]", orderId);
        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderCreatedEventToPaymentRequestAvroModel(domainEvent);
            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(), orderId, paymentRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallBack(orderServiceConfigData.getPaymentResponseTopicName(), paymentRequestAvroModel, paymentRequestAvroModel.getOrderId(), "PaymentRequestAvroModel"));
            log.info("PaymentRequestAvroModel sent to kafka for orderId : {]", paymentRequestAvroModel.getOrderId());
        } catch (Exception e) {
           log.error("Error while sending PaymentRequestAvroModel message to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }

}