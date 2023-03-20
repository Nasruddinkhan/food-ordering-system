package in.mypractice.food.ordering.domain.dto.create;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class OrderAddress {
    @NotNull
    @Max(value = 50)
    private final String street;
    @NotNull
    @Max(value = 50)
    private final String postalCode;
    @NotNull
    @Max(value = 50)
    private final String city;
}
