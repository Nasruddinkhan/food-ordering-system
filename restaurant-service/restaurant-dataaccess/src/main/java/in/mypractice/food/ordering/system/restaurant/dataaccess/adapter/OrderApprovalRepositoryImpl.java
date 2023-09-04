package in.mypractice.food.ordering.system.restaurant.dataaccess.adapter;


import in.mypractice.food.ordering.system.restaurant.dataaccess.mapper.RestaurantDataAccessMapper;
import in.mypractice.food.ordering.system.restaurant.dataaccess.repository.OrderApprovalJpaRepository;
import in.mypractice.food.ordering.system.restaurant.domain.entity.OrderApproval;
import in.mypractice.food.ordering.system.restaurant.service.domain.port.output.repository.OrderApprovalRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderApprovalRepositoryImpl implements OrderApprovalRepository {

    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    public OrderApprovalRepositoryImpl(OrderApprovalJpaRepository orderApprovalJpaRepository,
                                       RestaurantDataAccessMapper restaurantDataAccessMapper) {
        this.orderApprovalJpaRepository = orderApprovalJpaRepository;
        this.restaurantDataAccessMapper = restaurantDataAccessMapper;
    }

    @Override
    public OrderApproval save(OrderApproval orderApproval) {
        return restaurantDataAccessMapper
                .orderApprovalEntityToOrderApproval(orderApprovalJpaRepository
                        .save(restaurantDataAccessMapper.orderApprovalToOrderApprovalEntity(orderApproval)));
    }

}
