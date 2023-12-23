package in.mypractice.food.ordering.system.restaurant.service.domain.dto;

import in.mypractice.food.ordering.system.domain.valueobject.RestaurantOrderStatus;
import in.mypractice.food.ordering.system.restaurant.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantApprovalRequest {
    private String id;
    private String sagaId;
    private String restaurantId;
    private String orderId;
    private RestaurantOrderStatus restaurantOrderStatus;
    private java.util.List<Product> products;
    private java.math.BigDecimal price;
    private java.time.Instant createdAt;
}
