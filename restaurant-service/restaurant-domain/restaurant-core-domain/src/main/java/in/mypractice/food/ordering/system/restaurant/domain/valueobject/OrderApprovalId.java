package in.mypractice.food.ordering.system.restaurant.domain.valueobject;

import in.mypractice.food.ordering.system.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderApprovalId extends BaseId<UUID> {
    public OrderApprovalId(UUID value) {
        super(value);
    }
}
