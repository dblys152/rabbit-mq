package com.ys.rental.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class RentalId {

    @NotNull
    String id;
}
