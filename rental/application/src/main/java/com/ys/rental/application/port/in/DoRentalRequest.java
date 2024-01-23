package com.ys.rental.application.port.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DoRentalRequest {
    @NotNull
    private Long userId;
    @NotNull
    private List<CreateRentalLineRequest> rentalLineList;
    @NotNull
    private LocalDateTime startedAt;
    @NotNull
    private LocalDateTime endedAt;

    @Data
    public static class CreateRentalLineRequest {
        private Long productId;
        private int price;
        private int quantity;
    }
}
