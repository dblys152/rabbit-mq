package com.ys.product.domain.product;

import com.fasterxml.uuid.Generators;
import com.ys.product.refs.category.CategoryId;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.time.LocalDateTime;

@Entity(name = "PRODUCT_LIST")
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Product extends AbstractAggregateRoot<Product> {

    private static final LocalDateTime NOW = LocalDateTime.now();

    @EmbeddedId
    @Column(name = "PRODUCT_ID")
    private ProductId productId;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(name = "CATEGORY_ID", nullable = false)
    private CategoryId categoryId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PRICE", nullable = false)
    private Money price;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt = NOW;
    @Column(name = "MODIFIED_AT", nullable = false)
    private LocalDateTime modifiedAt = NOW;
    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;
    @Version
    @Column(name = "VERSION")
    private Long version;

    public static Product of(ProductId productId, ProductType type, CategoryId categoryId, String name, Money price, ProductStatus status, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt, Long version) {
        return new Product(productId, type, categoryId, name, price, status, createdAt, modifiedAt, deletedAt, version);
    }

    public Product(ProductId productId, ProductType type, CategoryId categoryId, String name, Money price, ProductStatus status) {
        this.productId = productId;
        this.type = type;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.status = status;
        validationStatus();
    }

    public static Product create(CreateProductCommand command) {
        ProductId productId = ProductId.of(Generators.timeBasedEpochGenerator().generate().toString());
        return new Product(
                productId,
                command.getType(),
                command.getCategoryId(),
                command.getName(),
                command.getPrice(),
                command.getStatus()
        );
    }

    public void change(ChangeProductCommand command) {
        this.categoryId = command.getCategoryId();
        this.name = command.getName();
        this.price = command.getPrice();
        this.status = command.getStatus();
        this.modifiedAt = NOW;
        validationStatus();
    }

    public void delete() {
        this.deletedAt = NOW;
    }

    public void changeStatus(ChangeStatusCommand command) {
        this.status = command.getStatus();
        validationStatus();
    }

    private void validationStatus() {
        if(this.type == ProductType.SALE_PRODUCT
                && !ProductStatus.SALE_STATUSES.contains(status)) {
            throw new IllegalArgumentException("판매 상품의 상태값이 아닙니다.");
        } else if(this.type == ProductType.RENTAL_PRODUCT
                && !ProductStatus.RENTAL_STATUSES.contains(status)) {
            throw new IllegalArgumentException("대여 상품의 상태값이 아닙니다.");
        }
    }
}
