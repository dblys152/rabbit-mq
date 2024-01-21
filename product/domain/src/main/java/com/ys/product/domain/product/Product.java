package com.ys.product.domain.product;

import com.ys.product.refs.category.domain.CategoryId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT_LIST")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Product extends AbstractAggregateRoot<Product> {
    @EmbeddedId
    @Column(name = "PRODUCT_ID")
    @NotNull
    private ProductId productId;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductType type;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductStatus status;

    @Column(name = "CATEGORY_ID", nullable = false)
    @NotNull
    private CategoryId categoryId;

    @Column(name = "NAME", nullable = false)
    @NotNull
    @Size(min = 1, max = 40)
    private String name;

    @Column(name = "PRICE", nullable = false)
    @NotNull
    private Money price;

    @Column(name = "CREATED_AT", nullable = false)
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT", nullable = false)
    @NotNull
    private LocalDateTime modifiedAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    @Version
    @Column(name = "VERSION")
    private Long version;

    public static Product of(ProductId productId, ProductType type, ProductStatus status, CategoryId categoryId, String name, Money price, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt, Long version) {
        return new Product(productId, type, status, categoryId, name, price, createdAt, modifiedAt, deletedAt, version);
    }

    public Product(ProductId productId, ProductType type, ProductStatus status, CategoryId categoryId, String name, Money price, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.productId = productId;
        this.type = type;
        this.status = status;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        validateStatus();
    }

    public static Product create(ProductId productId, CreateProductCommand command) {
        LocalDateTime now = LocalDateTime.now();
        return new Product(
                productId,
                command.getType(),
                command.getStatus(),
                command.getCategoryId(),
                command.getName(),
                command.getPrice(),
                now,
                now
        );
    }

    private void validateStatus() {
        if (isInvalidSaleProductStatus()) {
            throw new IllegalArgumentException("판매 상품의 상태 값이 아닙니다.");
        } else if (isInvalidRentalProductStatus()) {
            throw new IllegalArgumentException("대여 상품의 상태 값이 아닙니다.");
        }
    }

    private boolean isInvalidSaleProductStatus() {
        return this.type == ProductType.SALE_PRODUCT
                && !ProductStatus.SALE_STATUSES.contains(status);
    }

    private boolean isInvalidRentalProductStatus() {
        return this.type == ProductType.RENTAL_PRODUCT
                && !ProductStatus.RENTAL_STATUSES.contains(status);
    }

    public void change(ChangeProductCommand command) {
        this.status = command.getStatus();
        this.categoryId = command.getCategoryId();
        this.name = command.getName();
        this.price = command.getPrice();
        this.modifiedAt = LocalDateTime.now();
        validateStatus();
    }

    public void changeStatus(ChangeProductStatusCommand command) {
        this.status = command.getStatus();
        validateStatus();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
