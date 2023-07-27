package com.ys.product.domain.product;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class ChangeStatusCommand extends SelfValidating<ChangeStatusCommand> {

    @NotNull
    ProductStatus status;

    private ChangeStatusCommand(ProductStatus status) {
        this.status = status;
        validateSelf();
    }
}
