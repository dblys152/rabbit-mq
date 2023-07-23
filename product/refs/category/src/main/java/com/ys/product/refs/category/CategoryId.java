package com.ys.product.refs.category;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class CategoryId {

    @NotNull
    Integer id;
}
