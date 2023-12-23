package in.mypractice.food.ordering.system.restaurant.service.domain.port.input.message.impl;


import in.mypractice.food.ordering.system.restaurant.domain.event.OrderApprovalEvent;
import in.mypractice.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequest;
import in.mypractice.food.ordering.system.restaurant.service.domain.helper.RestaurantApprovalRequestHelper;
import in.mypractice.food.ordering.system.restaurant.service.domain.port.input.message.listener.RestaurantApprovalRequestMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RestaurantApprovalRequestMessageListenerImpl implements RestaurantApprovalRequestMessageListener {

    private final RestaurantApprovalRequestHelper restaurantApprovalRequestHelper;

    public RestaurantApprovalRequestMessageListenerImpl(RestaurantApprovalRequestHelper
                                                                restaurantApprovalRequestHelper) {
        this.restaurantApprovalRequestHelper = restaurantApprovalRequestHelper;
    }

    @Override
    public void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest) {
        OrderApprovalEvent orderApprovalEvent =
                restaurantApprovalRequestHelper.persistOrderApproval(restaurantApprovalRequest);
        orderApprovalEvent.fire();
    }
}
