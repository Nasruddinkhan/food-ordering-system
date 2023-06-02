package in.mypractice.food.ordering.system.messaging.publisher.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.function.BiConsumer;

@Component
@Slf4j
public class OrderKafkaMessageHelper {

//    public <T> ListenableFutureCallback<SendResult<String, T>> getKafkaCallBack(String responseTopicName, T requestAvroModel, String orderId, String requestAvroModelName) {
//        return new ListenableFutureCallback<SendResult<String, T>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                log.error("Error while sending "+ requestAvroModelName+ "message {} to topic {}", requestAvroModel.toString(), responseTopicName, ex);
//            }
//            @Override
//            public void onSuccess(SendResult<String, T> result) {
//                RecordMetadata metadata = result.getRecordMetadata();
//                log.info("Received successful response from kafka from order id: {} "+ "Topic: {} Partition: {} Offset: {} Timestamp: {}",
//                        orderId, metadata.topic(), metadata.partition(), metadata.offset(),metadata.timestamp());
//
//            }
//        };
//    }
    public <T> BiConsumer<SendResult<String, T>, Throwable> getKafkaCallBack(String responseTopicName, T requestAvroModel, String orderId, String requestAvroModelName) {
        return (result, ex) -> {
            if (ex != null) {
                log.error("Error while sending " + requestAvroModelName + " message {} to topic {}", requestAvroModel.toString(), responseTopicName, ex);
            } else {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received successful response from Kafka for order id: {} Topic: {} Partition: {} Offset: {} Timestamp: {}",
                        orderId, metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
            }
        };
    }
}
