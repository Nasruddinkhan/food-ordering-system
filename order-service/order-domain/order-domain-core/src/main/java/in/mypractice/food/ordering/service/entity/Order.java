package in.mypractice.food.ordering.service.entity;

import in.mypractice.food.ordering.system.domain.entity.AggregateRoot;
import in.mypractice.food.ordering.service.exception.OrderDomainException;
import in.mypractice.food.ordering.service.valueobject.OrderItemId;
import in.mypractice.food.ordering.service.valueobject.StreetAddress;
import in.mypractice.food.ordering.service.valueobject.TrackingId;
import in.mypractice.food.ordering.system.domain.valueobject.*;

import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {

    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;

    private final Money price;
    private final List<OrderItem> items;

    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessage;

    public static final String FAILURE_MESSAGE_DELIMITER= ",";
    public void initializeOrder(){
        setId(new OrderId(UUID.randomUUID()));
        this.trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }
    public void validateOrder(){
        validateInitialOrder();
        validateTotalPrice();
        validateItemPrice();

    }

    public void pay(){
        if( orderStatus != OrderStatus.PENDING) throw  new OrderDomainException("order is not in correct state for pay operation");
        orderStatus = OrderStatus.PAID;
    }
    public void approve(){
        if( orderStatus != OrderStatus.PAID) throw  new OrderDomainException("order is not in correct state for approve");
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessage){
        if( orderStatus != OrderStatus.PAID) throw  new OrderDomainException("order is not in correct state for init cancel");
        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessage(failureMessage);
    }

    private void updateFailureMessage(List<String> failureMessage) {
        if (this.failureMessage != null && failureMessage != null)
            this.failureMessage.addAll(failureMessage.stream().filter(msg -> !msg.isEmpty()).toList());
        if(this.failureMessage == null) {
            this.failureMessage = failureMessage;

        }
    }

    public void cancel(List<String> failureMessage){
        if( !(orderStatus != OrderStatus.CANCELLING || orderStatus != OrderStatus.PENDING)) throw  new OrderDomainException("order is not in correct state for  cancel");
        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessage(failureMessage);
    }
    private void validateItemPrice() {
      Money orderItemTotal = items.stream().map(orderItem -> {
           validateItemPrice(orderItem);
           return orderItem.getSubTotal();
       }).reduce(Money.ZERO,Money::add );
        if(!price.equals(orderItemTotal)) {
            throw new OrderDomainException("Total price: " + price.getAmount()
                + " is not equals to order item total: "+ orderItemTotal.getAmount()+"!");
        }

    }

    public void validateItemPrice(OrderItem orderItem){
        if(!orderItem.isPriceValid())
            throw new OrderDomainException("Order Item Price "+ orderItem.getPrice().getAmount()
             +" is not valid product "+ orderItem.getProduct().getId().getValue());

    }
    private void validateTotalPrice() {
        if (price == null || !price.isGreaterThanZero())
            throw new OrderDomainException("total price must be greater than zero");

    }

    private void validateInitialOrder() {
        if(orderStatus != null || getId() != null)
            throw new OrderDomainException("order is not correct state for initialization");
    }

    private void initializeOrderItems() {
        long itemId = 1;
        for (OrderItem orderItem: items){
            orderItem.initializeOrderItem(orderItem.getOrderId(), new OrderItemId(itemId++));
        }
    }
    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessage = builder.failureMessage;
    }


    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessage() {
        return failureMessage;
    }
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessage;

        private Builder() {
        }


        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessage(List<String> val) {
            failureMessage = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
