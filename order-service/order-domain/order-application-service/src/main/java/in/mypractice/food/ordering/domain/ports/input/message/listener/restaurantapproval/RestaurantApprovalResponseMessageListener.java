package in.mypractice.food.ordering.domain.ports.input.message.listener.restaurantapproval;

import in.mypractice.food.ordering.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {
    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);
}
