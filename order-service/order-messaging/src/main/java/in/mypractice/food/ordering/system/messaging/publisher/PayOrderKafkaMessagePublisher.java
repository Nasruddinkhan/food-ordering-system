package in.mypractice.food.ordering.system.messaging.publisher;

import in.mypractice.food.ordering.system.domain.config.OrderServiceConfigData;
import in.mypractice.food.ordering.system.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import in.mypractice.food.ordering.service.event.OrderPaidEvent;
import in.mypractice.food.ordering.system.messaging.mapper.OrderMessagingDataMapper;
import in.mypractice.food.ordering.system.messaging.publisher.helper.OrderKafkaMessageHelper;
import in.mypractice.food.ordering.system.kafka.producer.service.KafkaProducer;
import in.mypractice.food.ordering.system.order.avro.model.RestaurantApprovalRequestAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PayOrderKafkaMessagePublisher implements OrderPaidRestaurantRequestMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;

    public PayOrderKafkaMessagePublisher(OrderMessagingDataMapper orderMessagingDataMapper, OrderServiceConfigData orderServiceConfigData, KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer, OrderKafkaMessageHelper orderKafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.orderKafkaMessageHelper = orderKafkaMessageHelper;
    }

    @Override
    public void publish(OrderPaidEvent orderPaidEvent) {
       String orderId = orderPaidEvent.getOrder().getId().getValue().toString();

        log.info("Received OrderCreatedEvent for orderId : {]", orderId);
        try {
            RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel =
                    orderMessagingDataMapper.orderPaidEventToRestaurantApprovalRequestAvroModel(orderPaidEvent);
            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(), orderId, restaurantApprovalRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallBack(orderServiceConfigData.getPaymentResponseTopicName(), restaurantApprovalRequestAvroModel, orderId, "RestaurantApprovalRequestAvroModel"));
            log.info("PaymentRequestAvroModel sent to kafka for orderId : {]", restaurantApprovalRequestAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("Error while sending PaymentRequestAvroModel message to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }
}
