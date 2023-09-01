package in.mypractice.food.ordering.system.domain.ports.input.service.impl;

import in.mypractice.food.ordering.system.domain.dto.create.CreateOrderCommand;
import in.mypractice.food.ordering.system.domain.dto.create.CreateOrderResponse;
import in.mypractice.food.ordering.system.domain.dto.track.TrackOrderQuery;
import in.mypractice.food.ordering.system.domain.dto.track.TrackOrderResponse;
import in.mypractice.food.ordering.system.domain.ports.input.handler.OrderCreateCommandHandler;
import in.mypractice.food.ordering.system.domain.ports.input.handler.OrderTrackCommandHandler;
import in.mypractice.food.ordering.system.domain.ports.input.service.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Service
@Slf4j
@Validated
@RequiredArgsConstructor
 class OrderApplicationServiceImpl implements OrderApplicationService {
    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
