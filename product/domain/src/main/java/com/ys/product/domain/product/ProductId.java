package com.ys.product.domain.product;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Value(staticConstructor = "of")
public class ProductId {

    @NotNull
    Integer id;
}
