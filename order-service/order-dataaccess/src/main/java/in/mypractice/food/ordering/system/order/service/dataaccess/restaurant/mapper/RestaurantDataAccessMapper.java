package in.mypractice.food.ordering.system.order.service.dataaccess.restaurant.mapper;

import in.mypractice.food.ordering.domain.valueobject.Money;
import in.mypractice.food.ordering.domain.valueobject.ProductId;
import in.mypractice.food.ordering.domain.valueobject.RestaurantId;
import in.mypractice.food.ordering.service.entity.Product;
import in.mypractice.food.ordering.service.entity.Restaurant;
import in.mypractice.food.ordering.system.order.service.dataaccess.restaurant.entity.RestaurantEntity;
import in.mypractice.food.ordering.system.order.service.dataaccess.restaurant.exception.RestaurantDataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RestaurantDataAccessMapper {
    public List<UUID> restaurantToRestaurantProduct(Restaurant restaurant){
        return restaurant
                .getProducts().stream()
                .map(product -> product.getId().getValue()).toList();
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity = restaurantEntities.stream().findFirst()
                .orElseThrow(()-> new RestaurantDataAccessException("Restaurant could not be found"));
        List<Product> products = restaurantEntities.stream()
                .map(entity -> new Product(new ProductId(entity.getProductId()), entity.getProductName(), new Money(entity.getProductPrice()))).toList();
        return Restaurant.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
                .products(products)
                .active(restaurantEntity.getRestaurantActive())
                .build();
    }
}
