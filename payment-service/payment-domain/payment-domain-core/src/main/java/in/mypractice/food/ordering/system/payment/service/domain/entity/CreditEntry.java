package in.mypractice.food.ordering.system.payment.service.domain.entity;

import in.mypractice.food.ordering.system.domain.entity.BaseEntity;
import in.mypractice.food.ordering.system.domain.valueobject.CustomerId;
import in.mypractice.food.ordering.system.domain.valueobject.Money;
import in.mypractice.food.ordering.system.payment.service.domain.valueobject.CreditEntryId;

public class CreditEntry extends BaseEntity<CreditEntryId> {
    private final CustomerId customerId;
    private Money totalCreditAmount;

    private CreditEntry(Builder builder) {
        setId(builder.creditEntryId);
        customerId = builder.customerId;
        totalCreditAmount = builder.totalCreditAmount;
    }

    public static Builder builder() {
        return new Builder();
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Money getTotalCreditAmount() {
        return totalCreditAmount;
    }

    public void addCreditAMount(Money amount){
        totalCreditAmount = totalCreditAmount.add(amount);
    }
    public void subtractCreditAMount(Money amount){
        totalCreditAmount = totalCreditAmount.subtract(amount);
    }

    public static final class Builder {
        private CreditEntryId creditEntryId;
        private  CustomerId customerId;
        private Money totalCreditAmount;
        private Builder() {
        }
        public Builder creditEntryId(CreditEntryId val) {
            creditEntryId = val;
            return this;
        }

        public Builder totalCreditAmount(Money val) {
            totalCreditAmount = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }
        public CreditEntry build() {
            return new CreditEntry(this);
        }
    }
}
