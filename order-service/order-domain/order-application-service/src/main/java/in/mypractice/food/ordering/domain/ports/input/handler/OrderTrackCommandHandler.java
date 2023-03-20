package in.mypractice.food.ordering.domain.ports.input.handler;

import in.mypractice.food.ordering.domain.dto.track.TrackOrderQuery;
import in.mypractice.food.ordering.domain.dto.track.TrackOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderTrackCommandHandler {


    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}
