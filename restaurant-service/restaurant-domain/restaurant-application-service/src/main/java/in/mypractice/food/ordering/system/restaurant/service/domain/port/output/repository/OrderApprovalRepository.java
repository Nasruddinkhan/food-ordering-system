package in.mypractice.food.ordering.system.restaurant.service.domain.port.output.repository;


import in.mypractice.food.ordering.system.restaurant.domain.entity.OrderApproval;

public interface OrderApprovalRepository {
    OrderApproval save(OrderApproval orderApproval);
}
