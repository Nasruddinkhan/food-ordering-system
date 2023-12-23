package in.mypractice.food.ordering.system.restaurant.dataaccess.mapper;


import in.mypractice.food.ordering.system.domain.valueobject.Money;
import in.mypractice.food.ordering.system.domain.valueobject.OrderId;
import in.mypractice.food.ordering.system.domain.valueobject.ProductId;
import in.mypractice.food.ordering.system.domain.valueobject.RestaurantId;
import in.mypractice.food.ordering.system.restaurant.dataaccess.entity.OrderApprovalEntity;
import in.mypractice.food.ordering.system.restaurant.domain.entity.OrderApproval;
import in.mypractice.food.ordering.system.restaurant.domain.entity.OrderDetails;
import in.mypractice.food.ordering.system.restaurant.domain.entity.Product;
import in.mypractice.food.ordering.system.restaurant.domain.entity.Restaurant;
import in.mypractice.food.ordering.system.restaurant.domain.valueobject.OrderApprovalId;
import in.mypractice.food.ordering.system.restaurant.entity.RestaurantEntity;
import in.mypractice.food.ordering.system.restaurant.exception.RestaurantDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getOrderDetail().getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity =
                restaurantEntities.stream().findFirst().orElseThrow(() ->
                        new RestaurantDataAccessException("No restaurants found!"));

        List<Product> restaurantProducts = restaurantEntities.stream().map(entity ->
                        Product.builder()
                                .productId(new ProductId(entity.getProductId()))
                                .name(entity.getProductName())
                                .price(new Money(entity.getProductPrice()))
                                .available(entity.getProductAvailable())
                                .build())
                .collect(Collectors.toList());

        return Restaurant.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
                .orderDetail(OrderDetails.builder()
                        .products(restaurantProducts)
                        .build())
                .active(restaurantEntity.getRestaurantActive())
                .build();
    }

    public OrderApprovalEntity orderApprovalToOrderApprovalEntity(OrderApproval orderApproval) {
        return OrderApprovalEntity.builder()
                .id(orderApproval.getId().getValue())
                .restaurantId(orderApproval.getRestaurantId().getValue())
                .orderId(orderApproval.getOrderId().getValue())
                .status(orderApproval.getApprovalStatus())
                .build();
    }

    public OrderApproval orderApprovalEntityToOrderApproval(OrderApprovalEntity orderApprovalEntity) {
        return OrderApproval.builder()
                .orderApprovalId(new OrderApprovalId(orderApprovalEntity.getId()))
                .restaurantId(new RestaurantId(orderApprovalEntity.getRestaurantId()))
                .orderId(new OrderId(orderApprovalEntity.getOrderId()))
                .approvalStatus(orderApprovalEntity.getStatus())
                .build();
    }

}
