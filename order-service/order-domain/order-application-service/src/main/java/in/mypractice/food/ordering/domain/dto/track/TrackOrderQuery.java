package in.mypractice.food.ordering.domain.dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class TrackOrderQuery {
    private final UUID orderTrackingId;
}
