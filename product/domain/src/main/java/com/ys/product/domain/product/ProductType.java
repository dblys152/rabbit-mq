package com.ys.product.domain.product;

public enum ProductType {

    SALE_PRODUCT("판매 상품"),
    RENTAL_PRODUCT("렌탈 상품")
    ;

    final String description;

    ProductType(String description) {
        this.description = description;
    }
}
