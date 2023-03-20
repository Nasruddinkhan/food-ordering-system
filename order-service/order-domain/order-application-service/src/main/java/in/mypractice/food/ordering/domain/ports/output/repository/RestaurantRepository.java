package in.mypractice.food.ordering.domain.ports.output.repository;

import in.mypractice.food.ordering.service.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
  Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
