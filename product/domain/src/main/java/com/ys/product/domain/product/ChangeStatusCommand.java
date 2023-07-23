package com.ys.product.domain.product;

import com.ys.product.refs.category.CategoryId;
import jakarta.validation.Valid;
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
