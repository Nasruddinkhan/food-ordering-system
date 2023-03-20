package in.mypractice.food.ordering.domain.mapper;

import in.mypractice.food.ordering.domain.dto.create.CreateOrderCommand;
import in.mypractice.food.ordering.domain.dto.create.CreateOrderResponse;
import in.mypractice.food.ordering.domain.dto.create.OrderAddress;
import in.mypractice.food.ordering.domain.valueobject.CustomerId;
import in.mypractice.food.ordering.domain.valueobject.Money;
import in.mypractice.food.ordering.domain.valueobject.ProductId;
import in.mypractice.food.ordering.domain.valueobject.RestaurantId;
import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.entity.OrderItem;
import in.mypractice.food.ordering.service.entity.Product;
import in.mypractice.food.ordering.service.entity.Restaurant;
import in.mypractice.food.ordering.service.valueobject.StreetAddress;
import in.mypractice.food.ordering.service.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderDataMapper {
    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(orderItem -> new Product(new ProductId(orderItem.getProductId()))).toList())
                .build();
    }

    public Order createOrder(CreateOrderCommand createOrderCommand){
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToItemEntities(createOrderCommand.getItems()))
                .build();
    }

    private List<OrderItem> orderItemsToItemEntities(List<in.mypractice.food.ordering.domain.dto.create.OrderItem> items) {
    return items.stream().map(orderItem -> OrderItem.builder()
            .product(new Product(new ProductId(orderItem.getProductId())))
            .price(new Money(orderItem.getPrice()))
            .quantity(orderItem.getQuantity())
            .subTotal(new Money(orderItem.getSubTotal()))
            .build()).toList();
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress address) {
        
     return    new StreetAddress(UUID.randomUUID(), address.getCity(), address.getPostalCode(), address.getStreet());
    }

    public CreateOrderResponse createOrderResponse(Order order){
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
