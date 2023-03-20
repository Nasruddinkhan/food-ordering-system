package in.mypractice.food.ordering.domain.ports.input.handler;


import in.mypractice.food.ordering.domain.dto.create.CreateOrderCommand;
import in.mypractice.food.ordering.domain.dto.create.CreateOrderResponse;
import in.mypractice.food.ordering.domain.helper.OrderCreateHelper;
import in.mypractice.food.ordering.domain.mapper.OrderDataMapper;
import in.mypractice.food.ordering.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderCreateCommandHandler {
    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
      var orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
      log.info("order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
      orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
      return orderDataMapper.createOrderResponse(orderCreatedEvent.getOrder());
    }

}
