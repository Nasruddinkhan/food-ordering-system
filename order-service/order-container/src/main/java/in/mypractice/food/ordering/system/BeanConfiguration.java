package in.mypractice.food.ordering.system;

import in.mypractice.food.ordering.service.service.OrderDomainService;
import in.mypractice.food.ordering.service.service.impl.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public OrderDomainService orderDomainService(){
        return new OrderDomainServiceImpl();
    }
}
