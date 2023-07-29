package com.ys.rental.domain;

import com.ys.product.domain.product.CreateProductCommand;
import com.ys.rental.refs.user.domain.UserId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class CreateRentalCommand extends SelfValidating<CreateProductCommand> {

    @Valid @NotNull
    RentalId rentalId;
    @Valid @NotNull
    UserId userId;
    @Valid @NotNull
    RentalLines rentalLines;
    @NotNull
    LocalDateTime rentedAt;
    @NotNull
    LocalDateTime scheduledReturnAt;

    private CreateRentalCommand(
            RentalId rentalId,
            UserId userId,
            RentalLines rentalLines,
            LocalDateTime rentedAt,
            LocalDateTime scheduledReturnAt
    ) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.rentalLines = rentalLines;
        this.rentedAt = rentedAt;
        this.scheduledReturnAt = scheduledReturnAt;
        validateSelf();
    }
}
