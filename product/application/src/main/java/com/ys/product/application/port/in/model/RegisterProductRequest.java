package com.ys.product.application.port.in.model;

import com.ys.product.domain.product.ProductStatus;
import com.ys.product.domain.product.ProductType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterProductRequest {
    @NotNull
    private ProductType type;

    @NotNull
    private ProductStatus status;

    @NotNull
    private Integer categoryId;

    @NotNull
    @Size(min = 1, max = 40)
    private String name;

    @NotNull
    private int price;
}
