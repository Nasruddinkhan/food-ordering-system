package in.mypractice.food.ordering.system.domain.ports.output.repository;

import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findByTrackingId(TrackingId trackingId);


}
