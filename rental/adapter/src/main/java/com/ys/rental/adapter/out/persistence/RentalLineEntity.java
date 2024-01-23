package com.ys.rental.adapter.out.persistence;

import com.ys.rental.domain.Money;
import com.ys.rental.domain.RentalLine;
import com.ys.rental.refs.product.domain.ProductId;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity(name = "RENTAL_LINES")
@IdClass(RentalLineEntity.RentalLinePk.class)
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RentalLineEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RENTAL_ID", insertable = false)
    private RentalEntity rentalEntity;

    @Id
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Version
    @Column(name = "VERSION")
    Long version;

    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RentalLinePk implements Serializable {
        private RentalEntity rentalEntity;
        private Long productId;
    }

    public static RentalLineEntity fromDomain(RentalEntity rentalEntity, RentalLine rentalLine) {
        return new RentalLineEntity(
                rentalEntity,
                rentalLine.getProductId().getId(),
                rentalLine.getPrice().getValue(),
                rentalLine.getQuantity(),
                rentalLine.getVersion()
        );
    }

    public RentalLine toDomain() {
        return RentalLine.of(
                ProductId.of(this.productId), Money.of(this.price), this.quantity, this.version);
    }
}