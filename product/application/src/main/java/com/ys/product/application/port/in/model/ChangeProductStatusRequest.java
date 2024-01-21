package com.ys.product.application.port.in.model;

import com.ys.product.domain.product.ProductStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeProductStatusRequest {
    @NotNull
    private ProductStatus status;
}
