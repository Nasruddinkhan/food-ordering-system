package in.mypractice.food.ordering.system.payment.container;

import in.mypractice.food.ordering.system.payment.service.domain.service.PaymentDomainService;
import in.mypractice.food.ordering.system.payment.service.domain.service.impl.PaymentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainServiceImpl();
    }
}
