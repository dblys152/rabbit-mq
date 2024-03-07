package com.ys.product.domain.product;

import com.ys.shared.data.LongId;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Value(staticConstructor = "of")
public class ProductId implements LongId {
    @NotNull
    Long id;

    @Override
    public Long get() {
        return this.id;
    }
}
