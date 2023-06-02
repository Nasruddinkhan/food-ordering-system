package in.mypractice.food.ordering.system.messaging.listener;

import in.mypractice.food.ordering.system.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.system.kafka.consumer.KafkaConsumer;
import in.mypractice.food.ordering.system.messaging.mapper.OrderMessagingDataMapper;
import in.mypractice.food.ordering.system.order.avro.model.OrderApprovalStatus;
import in.mypractice.food.ordering.system.order.avro.model.RestaurantApprovalResponseAvroModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RestaurantApprovalResponseKafkaListener implements KafkaConsumer<RestaurantApprovalResponseAvroModel> {
    private final RestaurantApprovalResponseMessageListener restaurantApprovalResMsgListener ;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public RestaurantApprovalResponseKafkaListener(RestaurantApprovalResponseMessageListener restaurantApprovalResMsgListener, OrderMessagingDataMapper orderMessagingDataMapper) {
        this.restaurantApprovalResMsgListener = restaurantApprovalResMsgListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
            topics = "${order-service.restaurant-approval-response-topic-name}")
    public void receive(@Payload List<RestaurantApprovalResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET ) List<Long> offsets) {
        messages.forEach(responseAvroModel -> {
            if (OrderApprovalStatus.APPROVED == responseAvroModel.getOrderApprovalStatus()) {
                log.info("Processing approved order for order id: {}", responseAvroModel.getOrderId());
                restaurantApprovalResMsgListener.orderApproved(orderMessagingDataMapper.approvalResponseAvroModelToApprovalResponse(responseAvroModel));
            } else if (OrderApprovalStatus.REJECTED == responseAvroModel.getOrderApprovalStatus()) {
                log.info("Processing rejected order for order id: {}", responseAvroModel.getOrderId(), String.join(Order.FAILURE_MESSAGE_DELIMITER, responseAvroModel.getFailureMessages()));
                restaurantApprovalResMsgListener.orderRejected(orderMessagingDataMapper.approvalResponseAvroModelToApprovalResponse(responseAvroModel));
            }
        });

    }
}
