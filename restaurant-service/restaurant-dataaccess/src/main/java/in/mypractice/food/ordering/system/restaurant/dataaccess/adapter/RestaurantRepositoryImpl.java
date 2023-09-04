package in.mypractice.food.ordering.system.restaurant.dataaccess.adapter;


import in.mypractice.food.ordering.system.restaurant.dataaccess.mapper.RestaurantDataAccessMapper;
import in.mypractice.food.ordering.system.restaurant.domain.entity.Restaurant;
import in.mypractice.food.ordering.system.restaurant.entity.RestaurantEntity;
import in.mypractice.food.ordering.system.restaurant.repository.RestaurantJpaRepository;
import in.mypractice.food.ordering.system.restaurant.service.domain.port.output.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    public RestaurantRepositoryImpl(RestaurantJpaRepository restaurantJpaRepository,
                                    RestaurantDataAccessMapper restaurantDataAccessMapper) {
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.restaurantDataAccessMapper = restaurantDataAccessMapper;
    }

    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        List<UUID> restaurantProducts =
                restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant);
        Optional<List<RestaurantEntity>> restaurantEntities = restaurantJpaRepository
                .findByRestaurantIdAndProductIdIn(restaurant.getId().getValue(),
                        restaurantProducts);
        return restaurantEntities.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
