package com.ys.product.domain.product;

import com.ys.infrastructure.utils.SelfValidating;
import com.ys.product.refs.category.domain.CategoryId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateProductCommand extends SelfValidating<CreateProductCommand> {
    @NotNull
    private ProductType type;

    @NotNull
    private ProductStatus status;

    @Valid
    @NotNull
    private CategoryId categoryId;

    @NotNull
    @Size(min = 1, max = 40)
    private String name;

    @NotNull
    private Money price;

    public CreateProductCommand(ProductType type, ProductStatus status, CategoryId categoryId, String name, Money price) {
        this.type = type;
        this.status = status;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        validateSelf();
    }
}
