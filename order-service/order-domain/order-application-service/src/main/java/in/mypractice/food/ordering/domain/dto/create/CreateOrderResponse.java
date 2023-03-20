package in.mypractice.food.ordering.domain.dto.create;

import in.mypractice.food.ordering.domain.valueobject.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor

public class CreateOrderResponse {

    @NotNull
    private final UUID orderTrackingId;

    @NotNull
    private final OrderStatus orderStatus;

    @NotNull
    private final String message;
}
