package com.ys.product.domain.product;

import com.ys.infrastructure.utils.SelfValidating;
import com.ys.product.refs.category.domain.CategoryId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value(staticConstructor = "of")
public class CreateProductCommand extends SelfValidating<CreateProductCommand> {
    @NotNull
    ProductType type;

    @Valid
    @NotNull
    CategoryId categoryId;

    @NotNull
    @Size(min = 1, max = 40)
    String name;

    @NotNull
    Money price;

    @NotNull
    ProductStatus status;

    private CreateProductCommand(ProductType type, CategoryId categoryId, String name, Money price, ProductStatus status) {
        this.type = type;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.status = status;
        validateSelf();
    }
}
