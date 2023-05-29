package in.mypractice.food.ordering.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "in.mypractice.food.ordering.system.order.service.dataaccess")
@EntityScan(basePackages = "in.mypractice.food.ordering.system.order.service.dataaccess")
@SpringBootApplication(scanBasePackages = "in.mypractice.food.ordering.system")
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
