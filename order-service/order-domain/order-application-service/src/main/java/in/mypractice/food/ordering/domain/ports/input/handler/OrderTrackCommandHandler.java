package in.mypractice.food.ordering.domain.ports.input.handler;

import in.mypractice.food.ordering.domain.dto.track.TrackOrderQuery;
import in.mypractice.food.ordering.domain.dto.track.TrackOrderResponse;
import in.mypractice.food.ordering.domain.mapper.OrderDataMapper;
import in.mypractice.food.ordering.domain.ports.output.repository.OrderRepository;
import in.mypractice.food.ordering.service.exception.OrderNotFoundException;
import in.mypractice.food.ordering.service.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;


    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }
    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        var orderResult = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if ( orderResult.isEmpty() ) {
            log.info("could not find order with tracing id : {}", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("could not find order with tracing id: " + trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(orderResult.get());
    }
}
