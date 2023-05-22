package in.mypractice.food.ordering.application.rest;


import in.mypractice.food.ordering.domain.dto.create.CreateOrderCommand;
import in.mypractice.food.ordering.domain.dto.create.CreateOrderResponse;
import in.mypractice.food.ordering.domain.dto.track.TrackOrderQuery;
import in.mypractice.food.ordering.domain.dto.track.TrackOrderResponse;
import in.mypractice.food.ordering.domain.ports.input.service.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        log.info("OrderController.createOrder  for customer: {} at restaurant: {}", createOrderCommand.getCustomerId(), createOrderCommand.getRestaurantId());
        final var createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        log.info("Order Created with tracing id : {}", createOrderResponse.getOrderTrackingId());
        return ResponseEntity.ok(createOrderResponse);

    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponse> getOrderByTracingId(@PathVariable UUID trackingId){
        final var trackOrderResponse = orderApplicationService.trackOrder(TrackOrderQuery.builder().orderTrackingId(trackingId).build());
        log.info("Order Status with tracing id : {}", trackOrderResponse.getOrderTrackingId());
        return ResponseEntity.ok(trackOrderResponse);
    }
}
