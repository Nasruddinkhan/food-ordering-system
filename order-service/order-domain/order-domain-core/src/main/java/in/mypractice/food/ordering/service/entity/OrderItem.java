package in.mypractice.food.ordering.service.entity;

import in.mypractice.food.ordering.domain.entity.BaseEntity;
import in.mypractice.food.ordering.domain.valueobject.Money;
import in.mypractice.food.ordering.domain.valueobject.OrderId;
import in.mypractice.food.ordering.service.valueobject.OrderItemId;

import java.util.UUID;

public class OrderItem  extends BaseEntity<OrderItemId> {
    private  OrderId orderId;
    private final Product product;
    private final int quantity;
    private final Money price;

    private final Money subTotal;
    boolean isPriceValid(){
        return price.isGreaterThanZero()
                && price.equals(product.getPrice())
                && price.multiply(quantity).equals(subTotal);
    }

    private OrderItem(Builder builder) {

        super.setId(builder.orderItemId);
        product = builder.product;
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
    }

    public Money getPrice() {
        return price;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getSubTotal() {
        return subTotal;
    }

     void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderItemId orderItemId;
        private Product product;
        private int quantity;
        private Money price;
        private Money subTotal;

        private Builder() {
        }


        public Builder orderItemId(OrderItemId val) {
            orderItemId = val;
            return this;
        }

        public Builder product(Product val) {
            product = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder subTotal(Money val) {
            subTotal = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
