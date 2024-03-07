package com.ys.product.domain.product;

import com.ys.shared.utils.SelfValidating;
import com.ys.product.refs.category.domain.CategoryId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ChangeProductCommand extends SelfValidating<ChangeProductCommand> {
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

    public ChangeProductCommand(ProductStatus status, CategoryId categoryId, String name, Money price) {
        this.status = status;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        validateSelf();
    }
}
