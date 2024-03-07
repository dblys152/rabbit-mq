package com.ys.product.refs.rental.domain;

import com.ys.shared.data.LongId;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class RentalId implements LongId {
    @NotNull
    private Long id;

    @Override
    public Long get() {
        return this.id;
    }
}
