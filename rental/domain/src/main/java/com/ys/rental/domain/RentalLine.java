package com.ys.rental.domain;

import com.ys.rental.refs.product.domain.ProductId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Value(staticConstructor = "of")
public class RentalLine {
    @Valid
    @NotNull
    private ProductId productId;

    @NotNull
    private Money price;

    private int quantity;

    private Long version;

    public static RentalLine create(ProductId productId, Money price, int quantity) {
        return new RentalLine(productId, price, quantity, null);
    }

    private void validation() {
        if (this.quantity <= 0) {
            throw new IllegalArgumentException("수량은 한 개 이상이어야 합니다.");
        }
    }
}