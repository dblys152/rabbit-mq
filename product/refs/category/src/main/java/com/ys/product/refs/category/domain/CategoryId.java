package com.ys.product.refs.category.domain;

import com.ys.shared.data.IntegerId;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class CategoryId implements IntegerId {
    @NotNull
    Integer id;

    @Override
    public Integer get() {
        return this.id;
    }
}
