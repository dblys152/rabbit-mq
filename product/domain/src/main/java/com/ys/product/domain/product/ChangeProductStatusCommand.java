package com.ys.product.domain.product;

import com.ys.infrastructure.utils.SelfValidating;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class ChangeProductStatusCommand extends SelfValidating<ChangeProductStatusCommand> {
    @NotNull
    ProductStatus status;

    private ChangeProductStatusCommand(ProductStatus status) {
        this.status = status;
        validateSelf();
    }
}
