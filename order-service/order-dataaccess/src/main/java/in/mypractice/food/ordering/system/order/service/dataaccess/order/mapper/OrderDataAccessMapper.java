package in.mypractice.food.ordering.system.order.service.dataaccess.order.mapper;


import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.entity.OrderItem;
import in.mypractice.food.ordering.service.entity.Product;
import in.mypractice.food.ordering.service.valueobject.OrderItemId;
import in.mypractice.food.ordering.service.valueobject.StreetAddress;
import in.mypractice.food.ordering.service.valueobject.TrackingId;
import in.mypractice.food.ordering.system.domain.valueobject.*;
import in.mypractice.food.ordering.system.order.service.dataaccess.order.entity.OrderAddressEntity;
import in.mypractice.food.ordering.system.order.service.dataaccess.order.entity.OrderEntity;
import in.mypractice.food.ordering.system.order.service.dataaccess.order.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;

import static in.mypractice.food.ordering.service.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .price(order.getPrice().getAmount())
                .items(orderItemToOrderItemEntities(order.getItems()))
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessage() != null ? String.join(FAILURE_MESSAGE_DELIMITER, order.getFailureMessage()):"" )
                .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .orderId(new OrderId(orderEntity.getId() ))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .deliveryAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
                .price(new Money(orderEntity.getPrice()))
                .items(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMessage(orderEntity.getFailureMessages().isEmpty() ? List.of(): List.of(orderEntity.getFailureMessages().split(FAILURE_MESSAGE_DELIMITER)))
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream().map(orderItemEntity -> OrderItem.builder()
                .orderItemId(new OrderItemId(orderItemEntity.getId()))
                .product(new Product(new ProductId(orderItemEntity.getProductId())))
                .price(new Money(orderItemEntity.getPrice()))
                .quantity(orderItemEntity.getQuantity())
                .subTotal(new Money(orderItemEntity.getSubTotal()))

                .build()).toList();
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(address.getId(), address.getStreet(), address.getPostalCode(), address.getCity());
    }

    private List<OrderItemEntity> orderItemToOrderItemEntities(List<OrderItem> items) {
        return items.stream()
                .map(orderItem -> OrderItemEntity.builder()
                        .id(orderItem.getId().getValue())
                        .productId(orderItem.getProduct().getId().getValue())
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubTotal().getAmount())
                        .build()).toList();
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
       return OrderAddressEntity.builder()
               .id(deliveryAddress.getId())
               .city(deliveryAddress.getCity())
               .postalCode(deliveryAddress.getPostalCode())
               .street(deliveryAddress.getStreet())
               .build();
    }
}
