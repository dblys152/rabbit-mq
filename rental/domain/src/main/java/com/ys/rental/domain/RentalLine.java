package com.ys.rental.domain;

import com.ys.rental.refs.product.domain.ProductId;
import lombok.*;

@Value(staticConstructor = "of")
public class RentalLine {
    private ProductId productId;
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