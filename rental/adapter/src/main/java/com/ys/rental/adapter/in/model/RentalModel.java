package com.ys.rental.adapter.in.model;

import com.ys.rental.domain.Rental;
import com.ys.rental.domain.RentalLines;
import com.ys.rental.domain.RentalPeriod;
import com.ys.rental.domain.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class RentalModel {
    Long rentalId;
    Long userId;
    RentalStatus status;
    List<RentalLineModel> rentalLines;
    LocalDateTime startedAt;
    LocalDateTime endedAt;
    LocalDateTime returnedAt;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;

    public static RentalModel fromDomain(Rental rental) {
        RentalLines rentalLines = rental.getRentalLines();
        RentalPeriod rentalPeriod = rental.getRentalPeriod();
        return new RentalModel(
                rental.getRentalId().get(),
                rental.getUserId().get(),
                rental.getStatus(),
                rentalLines.getItems().stream()
                        .map(rl -> new RentalLineModel(rl.getProductId().get(), rl.getPrice().getValue(), rl.getQuantity()))
                        .toList(),
                rentalPeriod.getStartedAt(),
                rentalPeriod.getEndedAt(),
                rental.getReturnedAt(),
                rental.getCreatedAt(),
                rental.getModifiedAt()
        );
    }

    @Data
    @AllArgsConstructor
    public static class RentalLineModel {
        Long productId;
        int price;
        int quantity;
    }
}
