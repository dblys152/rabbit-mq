package com.ys.rental.domain;

import com.ys.infrastructure.utils.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DoReturnCommand extends SelfValidating<DoReturnCommand> {
    @NotNull
    private LocalDateTime returnedAt;

    public DoReturnCommand(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
        validateSelf();
    }
}
