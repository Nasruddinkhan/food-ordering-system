package in.mypractice.food.ordering.domain;

import in.mypractice.food.ordering.domain.dto.create.CreateOrderCommand;
import in.mypractice.food.ordering.domain.dto.create.OrderAddress;
import in.mypractice.food.ordering.domain.dto.create.OrderItem;
import in.mypractice.food.ordering.domain.mapper.OrderDataMapper;
import in.mypractice.food.ordering.domain.ports.output.repository.CustomerRepository;
import in.mypractice.food.ordering.domain.ports.output.repository.OrderRepository;
import in.mypractice.food.ordering.domain.ports.output.repository.RestaurantRepository;
import in.mypractice.food.ordering.domain.valueobject.*;
import in.mypractice.food.ordering.service.entity.Customer;
import in.mypractice.food.ordering.service.entity.Order;
import in.mypractice.food.ordering.service.entity.Product;
import in.mypractice.food.ordering.service.entity.Restaurant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationTest {

    @Autowired
    private OrderApplicationTest orderApplicationTest;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;

    private final UUID CUSTOMER_ID = UUID.fromString("ff1f4099-b800-43a8-82ff-fbbb9dd8875e");
    private final UUID RESTAURANT_ID = UUID.fromString("ff1f4099-b800-43a8-82ff-fbbb9dd8876e");
    private final UUID PRODUCT_ID = UUID.fromString("ff1f4099-b800-43a8-82ff-fbbb9dd8877e");
    private final UUID ORDER_ID = UUID.fromString("ff1f4099-b800-43a8-82ff-fbbb9dd8878e");

    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeAll
    public void  init() {
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("LBS MARG")
                        .postalCode("400083")
                        .city("Mumbai")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00")).build(),
                        OrderItem.builder()
                        .productId(PRODUCT_ID)
                        .quantity(3)
                        .price(new BigDecimal("50.00"))
                        .subTotal(new BigDecimal("150.00"))
                        .build()))
                .build();
        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("LBS MARG")
                        .postalCode("400083")
                        .city("Mumbai")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00")).build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        createOrderCommandWrongProductPrice= CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("LBS MARG")
                        .postalCode("400083")
                        .city("Mumbai")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00")).build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Restaurant restaurantResponse = Restaurant.builder().
                restaurantId(new RestaurantId(RESTAURANT_ID))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2   ", new Money(new BigDecimal("50.00")))
                ))
                .build();

        Order order = orderDataMapper.createOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        Mockito.when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        Mockito.when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
    }




}
