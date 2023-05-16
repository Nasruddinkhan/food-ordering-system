package in.mypractice.food.ordering.domain;



import in.mypractice.food.ordering.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import in.mypractice.food.ordering.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import in.mypractice.food.ordering.domain.ports.output.message.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import in.mypractice.food.ordering.domain.ports.output.repository.CustomerRepository;
import in.mypractice.food.ordering.domain.ports.output.repository.OrderRepository;
import in.mypractice.food.ordering.domain.ports.output.repository.RestaurantRepository;
import in.mypractice.food.ordering.service.service.OrderDomainService;
import in.mypractice.food.ordering.service.service.impl.OrderDomainServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "in.mypractice.food.ordering")
public class OrderTestConfiguration {

    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher() {
        return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return Mockito.mock(RestaurantRepository.class);
    }



    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }

}
