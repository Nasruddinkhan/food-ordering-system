package in.mypractice.food.ordering.system.domain.ports.input.service;

import in.mypractice.food.ordering.system.domain.dto.create.CreateOrderCommand;
import in.mypractice.food.ordering.system.domain.dto.create.CreateOrderResponse;
import in.mypractice.food.ordering.system.domain.dto.track.TrackOrderQuery;
import in.mypractice.food.ordering.system.domain.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);

}
