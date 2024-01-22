package com.ys.rental.domain;

import com.ys.rental.refs.product.domain.ProductId;
import lombok.*;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class RentalLine {
    private ProductId productId;
    private Money price;
    private int quantity;

    Long version;

    private RentalLine(ProductId productId, Money price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        validation();
    }

    public static RentalLine create(ProductId productId, Money price, int quantity) {
        return new RentalLine(productId, price, quantity);
    }

    private void validation() {
        if (this.quantity <= 0) {
            throw new IllegalArgumentException("수량은 한 개 이상이어야 합니다.");
        }
    }
}