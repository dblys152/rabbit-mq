package com.ys.product.domain.product;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Value(staticConstructor = "of")
public class ProductId {

    @NotNull
    String id;
}
