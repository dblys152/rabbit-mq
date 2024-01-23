package com.ys.rental.domain;

import com.ys.infrastructure.utils.SelfValidating;
import com.ys.rental.refs.user.domain.UserId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DoRentalCommand extends SelfValidating<DoRentalCommand> {
    @Valid
    @NotNull
    private UserId userId;

    @Valid
    @NotNull
    private RentalLines rentalLines;

    @Valid
    @NotNull
    private RentalPeriod rentalPeriod;

    public DoRentalCommand(
            UserId userId,
            RentalLines rentalLines,
            RentalPeriod rentalPeriod
    ) {
        this.userId = userId;
        this.rentalLines = rentalLines;
        this.rentalPeriod = rentalPeriod;
        validateSelf();
    }
}
