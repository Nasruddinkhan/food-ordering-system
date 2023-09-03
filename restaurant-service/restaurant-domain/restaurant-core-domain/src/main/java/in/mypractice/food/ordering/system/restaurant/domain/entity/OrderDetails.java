package in.mypractice.food.ordering.system.restaurant.domain.entity;

import in.mypractice.food.ordering.system.domain.entity.BaseEntity;
import in.mypractice.food.ordering.system.domain.valueobject.Money;
import in.mypractice.food.ordering.system.domain.valueobject.OrderId;
import in.mypractice.food.ordering.system.domain.valueobject.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Data
@Builder
public class OrderDetails extends BaseEntity<OrderId> {
    private OrderStatus orderStatus;
    private Money totalAmount;

    private final List<Product> products;
}
