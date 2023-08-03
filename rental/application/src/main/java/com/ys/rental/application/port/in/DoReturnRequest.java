package com.ys.rental.application.port.in;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DoReturnRequest {

    @NotNull
    private LocalDateTime returnedAt;
}
