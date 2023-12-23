package in.mypractice.food.ordering.system.payment.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "in.mypractice.food.ordering.system.payment.dataaccess")
@EntityScan(basePackages = "in.mypractice.food.ordering.system.payment.dataaccess")
@SpringBootApplication(scanBasePackages = "in.mypractice.food.ordering.system")
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
