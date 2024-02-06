package com.ys.product.refs.rental.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RentalEvent {
    private Long rentalId;

    private Long userId;

    private RentalStatus status;

    private List<RentalLineEvent> rentalLineList;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private LocalDateTime returnedAt;

    @AllArgsConstructor
    @Getter
    public static class RentalLineEvent {
        private Long productId;
        private int price;
        private int quantity;
    }
}
