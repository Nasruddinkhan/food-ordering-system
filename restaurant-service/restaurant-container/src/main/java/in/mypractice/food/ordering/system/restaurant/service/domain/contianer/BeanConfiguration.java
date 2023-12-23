package in.mypractice.food.ordering.system.restaurant.service.domain.contianer;

import in.mypractice.food.ordering.system.restaurant.domain.service.RestaurantDomainService;
import in.mypractice.food.ordering.system.restaurant.domain.service.RestaurantDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RestaurantDomainService restaurantDomainService() {
        return new RestaurantDomainServiceImpl();
    }

}
