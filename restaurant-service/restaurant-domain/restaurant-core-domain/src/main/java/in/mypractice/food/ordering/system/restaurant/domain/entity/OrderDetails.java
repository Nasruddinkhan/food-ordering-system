package in.mypractice.food.ordering.system.restaurant.domain.entity;

import in.mypractice.food.ordering.system.domain.entity.BaseEntity;
import in.mypractice.food.ordering.system.domain.valueobject.Money;
import in.mypractice.food.ordering.system.domain.valueobject.OrderId;
import in.mypractice.food.ordering.system.domain.valueobject.OrderStatus;

import java.util.List;
import java.util.Objects;


public class OrderDetails extends BaseEntity<OrderId> {
    private OrderStatus orderStatus;
    private Money totalAmount;
    private final List<Product> products;

    private OrderDetails(Builder builder) {
        setId(builder.orderId);
        orderStatus = builder.orderStatus;
        totalAmount = builder.totalAmount;
        products = builder.products;
    }

    public static Builder builder() {
        return new Builder();
    }


    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public static final class Builder {
        private OrderId orderId;
        private OrderStatus orderStatus;
        private Money totalAmount;
        private List<Product> products;

        private Builder() {
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder totalAmount(Money val) {
            totalAmount = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public OrderDetails build() {
            return new OrderDetails(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetails that)) return false;
        if (!super.equals(o)) return false;
        return orderStatus == that.orderStatus && Objects.equals(totalAmount, that.totalAmount) && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderStatus, totalAmount, products);
    }
}
