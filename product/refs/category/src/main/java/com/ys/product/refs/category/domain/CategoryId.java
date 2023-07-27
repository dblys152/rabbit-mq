package com.ys.product.refs.category.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class CategoryId {

    @NotNull
    String id;
}
