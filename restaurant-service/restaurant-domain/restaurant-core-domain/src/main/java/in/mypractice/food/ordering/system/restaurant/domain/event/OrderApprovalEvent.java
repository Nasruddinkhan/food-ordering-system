package in.mypractice.food.ordering.system.restaurant.domain.event;

import in.mypractice.food.ordering.system.domain.events.DomainEvent;
import in.mypractice.food.ordering.system.domain.valueobject.RestaurantId;
import in.mypractice.food.ordering.system.restaurant.domain.entity.OrderApproval;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
@RequiredArgsConstructor
@Getter
public abstract class OrderApprovalEvent implements DomainEvent<OrderApproval> {
    private final OrderApproval orderApproval;
    private final RestaurantId restaurantId;
    private final List<String> failureMessages;
    private final ZonedDateTime createdAt;
}
