package com.ys.rental.domain;

import com.ys.product.domain.product.ProductId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Value;

@Value(staticConstructor = "of")
@Embeddable
public class RentalLine {

    private static final int ZERO = 0;

    @Column(name = "PRODUCT_ID", nullable = false)
    ProductId productId;
    @Column(name = "QUANTITY", nullable = false)
    int quantity;

    private RentalLine(ProductId productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        validation();
    }

    private void validation() {
        if (this.quantity <= ZERO) {
            throw new IllegalArgumentException("수량은 한 개 이상이어야 합니다.");
        }
    }
}