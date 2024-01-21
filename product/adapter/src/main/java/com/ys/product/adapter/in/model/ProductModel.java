package com.ys.product.adapter.in.model;

import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductStatus;
import com.ys.product.domain.product.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductModel {
    private Long productId;
    private ProductType type;
    private ProductStatus status;
    private Integer categoryId;
    private String name;
    private int price;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static ProductModel fromDomain(Product product) {
        return new ProductModel(
                product.getProductId().get(),
                product.getType(),
                product.getStatus(),
                product.getCategoryId().get(),
                product.getName(),
                product.getPrice().getValue(),
                product.getCreatedAt(),
                product.getModifiedAt()
        );
    }
}
