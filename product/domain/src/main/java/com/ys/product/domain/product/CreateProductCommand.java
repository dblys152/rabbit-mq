package com.ys.product.domain.product;

import com.ys.product.refs.category.CategoryId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class CreateProductCommand extends SelfValidating<CreateProductCommand> {

    @NotNull
    ProductType type;
    @Valid @NotNull
    CategoryId categoryId;
    @NotNull
    String name;
    @NotNull
    Money price;
    @NotNull
    ProductStatus status;

    private CreateProductCommand(
            ProductType type,
            CategoryId categoryId,
            String name,
            Money price,
            ProductStatus status
    ) {
        this.type = type;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.status = status;
        validateSelf();
    }
}
