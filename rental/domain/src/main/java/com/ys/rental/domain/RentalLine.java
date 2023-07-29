package com.ys.rental.domain;

import com.ys.product.domain.product.ProductId;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "RENTAL_LINE_LIST")
@IdClass(RentalLine.RentalLinePk.class)
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class RentalLine {

    private static final int ZERO = 0;

    @Id
    @Column(name = "RENTAL_ID", nullable = false)
    private RentalId rentalId;
    @Id
    @Column(name = "PRODUCT_ID", nullable = false)
    ProductId productId;
    @Column(name = "PRICE", nullable = false)
    Money price;
    @Column(name = "QUANTITY", nullable = false)
    int quantity;
    @Version
    Long version;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class RentalLinePk implements Serializable {
        private RentalId rentalId;
        private ProductId productId;
    }

    private RentalLine(RentalId rentalId, ProductId productId, Money price, int quantity) {
        this.rentalId = rentalId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        validation();
    }

    public static RentalLine of(RentalId rentalId, ProductId productId, Money price, int quantity) {
        return new RentalLine(rentalId, productId, price, quantity);
    }

    private void validation() {
        if (this.quantity <= ZERO) {
            throw new IllegalArgumentException("수량은 한 개 이상이어야 합니다.");
        }
    }
}