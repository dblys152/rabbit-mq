package com.ys.product.domain.product;

import java.util.Set;

public enum ProductStatus {
    SALE_AVAILABLE("판매 가능"),
    SALE_STOPPED("판매 중단"),

    RENTAL_AVAILABLE("대여 가능"),
    ON_LOAN("대여중"),
    RENTAL_STOPPED("대여 중단"),
    UNDER_REPAIR("수리중"),
    ;

    public static final Set<ProductStatus> SALE_STATUSES = Set.of(
            SALE_AVAILABLE, SALE_STOPPED);
    public static final Set<ProductStatus> RENTAL_STATUSES = Set.of(
            RENTAL_AVAILABLE, ON_LOAN, RENTAL_STOPPED, UNDER_REPAIR);

    final String description;

    ProductStatus(final String description) {
        this.description = description;
    }
}
