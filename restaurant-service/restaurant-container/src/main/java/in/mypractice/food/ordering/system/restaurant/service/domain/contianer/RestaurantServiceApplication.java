package in.mypractice.food.ordering.system.restaurant.service.domain.contianer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"in.mypractice.food.ordering.system.restaurant.dataaccess.repository","in.mypractice.food.ordering.system.restaurant.dataaccess","in.mypractice.food.ordering.system.restaurant"})
@EntityScan(basePackages = {"in.mypractice.food.ordering.system.restaurant.dataaccess","in.mypractice.food.ordering.system.restaurant"})
@SpringBootApplication(scanBasePackages = {"in.mypractice.food.ordering.system", "in.mypractice.food.ordering.system.restaurant"})
public class RestaurantServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
    }
}
