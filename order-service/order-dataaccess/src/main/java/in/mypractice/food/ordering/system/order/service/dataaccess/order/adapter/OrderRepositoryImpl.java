package in.mypractice.food.ordering.system.order.service.dataaccess.order.adapter;

import in.mypractice.food.ordering.domain.ports.output.repository.OrderRepository;
import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.valueobject.TrackingId;
import in.mypractice.food.ordering.system.order.service.dataaccess.order.mapper.OrderDataAccessMapper;
import in.mypractice.food.ordering.system.order.service.dataaccess.order.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    public  final OrderJpaRepository orderJpaRepository;
    public final OrderDataAccessMapper orderDataAccessMapper;
    @Override
    public Order save(Order order) {
       return orderDataAccessMapper.orderEntityToOrder(orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order))) ;
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository.findByTracking(trackingId.getValue())
                .map(orderDataAccessMapper::orderEntityToOrder);
    }
}
