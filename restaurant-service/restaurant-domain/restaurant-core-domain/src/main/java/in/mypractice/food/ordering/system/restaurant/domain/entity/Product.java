package in.mypractice.food.ordering.system.restaurant.domain.entity;

import in.mypractice.food.ordering.system.domain.entity.BaseEntity;
import in.mypractice.food.ordering.system.domain.valueobject.Money;
import in.mypractice.food.ordering.system.domain.valueobject.ProductId;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@Data
public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;
    private final int quantity;
    private boolean available;

    public void updateWithConfirmedNamePriceAndAvailability(String name, Money price, boolean available) {
        this.available = available;
        this.name = name;
        this.price = price;
    }
}
