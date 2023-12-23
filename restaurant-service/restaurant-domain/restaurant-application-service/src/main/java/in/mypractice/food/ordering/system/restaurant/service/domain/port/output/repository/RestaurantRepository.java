package in.mypractice.food.ordering.system.restaurant.service.domain.port.output.repository;


import in.mypractice.food.ordering.system.restaurant.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
