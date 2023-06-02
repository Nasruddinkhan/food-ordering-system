package in.mypractice.food.ordering.system.order.service.dataaccess.restaurant.adapter;

import in.mypractice.food.ordering.system.domain.ports.output.repository.RestaurantRepository;
import in.mypractice.food.ordering.service.entity.Restaurant;
import in.mypractice.food.ordering.system.order.service.dataaccess.restaurant.mapper.RestaurantDataAccessMapper;
import in.mypractice.food.ordering.system.order.service.dataaccess.restaurant.repository.RestaurantJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    public RestaurantRepositoryImpl(RestaurantJpaRepository restaurantJpaRepository, RestaurantDataAccessMapper restaurantDataAccessMapper) {
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.restaurantDataAccessMapper = restaurantDataAccessMapper;
    }

    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        var restaurantProducts = restaurantDataAccessMapper.restaurantToRestaurantProduct(restaurant);
        var restaurantsEntities =  restaurantJpaRepository.findByRestaurantIdAndProductIdIn(restaurant.getId().getValue(), restaurantProducts);
        return restaurantsEntities.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
