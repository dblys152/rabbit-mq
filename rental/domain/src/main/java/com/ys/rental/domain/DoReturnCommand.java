package com.ys.rental.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class DoReturnCommand extends SelfValidating<DoReturnCommand> {

    @NotNull
    LocalDateTime returnedAt;

    private DoReturnCommand(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
        validateSelf();
    }
}
