package in.mypractice.food.ordering.system.restaurant.domain.entity;

import in.mypractice.food.ordering.system.domain.entity.AggregateRoot;
import in.mypractice.food.ordering.system.domain.valueobject.*;
import in.mypractice.food.ordering.system.restaurant.domain.valueobject.OrderApprovalId;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Getter
public class Restaurant extends AggregateRoot<RestaurantId> {
    private OrderApproval orderApproval;
    private boolean active;
    private final OrderDetails orderDetails;

    public void validateOrder(List<String> failureMessages) {
        if (orderDetails.getOrderStatus() != OrderStatus.PAID) {
            failureMessages.add(String.format("Payment is not completed for the order id : %s", orderDetails.getId()));
        }
        Money totalAmount = orderDetails.getProducts().stream().map(product -> {
            if (!product.isAvailable()) {
                failureMessages.add(String.format("Product with id: %s is not available ", product.getId().getValue()));
            }
            return product.getPrice().multiply(product.getQuantity());
        }).reduce(Money.ZERO, Money::add);

        if (!totalAmount.equals(orderDetails.getTotalAmount())) {
            failureMessages.add(String.format("price total is not correct for order %s", orderDetails.getId()));
        }
    }

    public void constructOrderApproval(OrderApprovalStatus orderApprovalStatus) {
        this.orderApproval = OrderApproval.builder()
                .orderApprovalId(new OrderApprovalId(UUID.randomUUID()))
                .orderId(orderDetails.getId())
                .restaurantId(this.getId())
                .approvalStatus(orderApprovalStatus)
                .build();
    }
}