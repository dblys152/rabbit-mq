package com.ys.rental.refs.product.domain;

import com.ys.shared.data.LongId;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class ProductId implements LongId {
    @NotNull
    Long id;

    @Override
    public Long get() {
        return this.id;
    }
}
