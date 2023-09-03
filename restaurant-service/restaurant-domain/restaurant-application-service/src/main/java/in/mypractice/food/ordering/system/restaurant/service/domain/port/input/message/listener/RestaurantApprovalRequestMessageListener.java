package in.mypractice.food.ordering.system.restaurant.service.domain.port.input.message.listener;


import in.mypractice.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest;

public interface RestaurantApprovalRequestMessageListener {
    void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);
}
