package com.ys.product.domain.product;

import com.ys.infrastructure.utils.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChangeProductStatusCommand extends SelfValidating<ChangeProductStatusCommand> {
    @NotNull
    private ProductStatus status;

    public ChangeProductStatusCommand(ProductStatus status) {
        this.status = status;
        validateSelf();
    }
}
