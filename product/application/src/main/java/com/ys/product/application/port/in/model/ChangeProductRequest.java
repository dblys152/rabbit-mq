package com.ys.product.application.port.in.model;

import com.ys.product.domain.product.ProductStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangeProductRequest {
    @NotNull
    private ProductStatus status;

    @Valid
    @NotNull
    private Integer categoryId;

    @NotNull
    @Size(min = 1, max = 40)
    private String name;

    @NotNull
    private int price;
}
