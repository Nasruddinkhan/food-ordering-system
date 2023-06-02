package in.mypractice.food.ordering.service.valueobject;

import in.mypractice.food.ordering.system.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long orderItemId){
        super(orderItemId);
    }
}
