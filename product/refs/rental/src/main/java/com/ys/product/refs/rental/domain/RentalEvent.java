package com.ys.product.refs.rental.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Getter
    public static class RentalLineEvent {
        private Long productId;
        private int price;
        private int quantity;

        @JsonCreator
        public RentalLineEvent(@JsonProperty("productId") Long productId,
                               @JsonProperty("price") int price,
                               @JsonProperty("quantity") int quantity) {
            this.productId = productId;
            this.price = price;
            this.quantity = quantity;
        }
    }
}
