package com.ys.product.domain.product;

import com.ys.infrastructure.utils.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangeProductStatusCommand extends SelfValidating<ChangeProductStatusCommand> {
    @NotNull
    private ProductId productId;
    @NotNull
    private ProductStatus status;

    public ChangeProductStatusCommand(ProductId productId, ProductStatus status) {
        this.productId = productId;
        this.status = status;
        validateSelf();
    }
}
