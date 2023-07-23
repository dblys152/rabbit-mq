package com.ys.product.domain.product.fixture;

import com.ys.product.domain.product.*;
import com.ys.product.refs.category.CategoryId;

import java.time.LocalDateTime;

public class SupportProductFixture {

    protected static final LocalDateTime NOW = LocalDateTime.now();
    protected static final ProductId PRODUCT_ID = ProductId.of(123);
    protected static final CategoryId CATEGORY_ID = CategoryId.of(1234);
    protected static final String PRODUCT_NAME = "PRODUCT_NAME";
    protected static final Money MONEY_1000 = Money.of(1000);
    protected static final Money MONEY_2000 = Money.of(2000);
    protected static final CreateProductCommand CREATE_PRODUCT_COMMAND = CreateProductCommand.of(
            ProductType.RENTAL_PRODUCT, CATEGORY_ID, PRODUCT_NAME, MONEY_1000, ProductStatus.RENTAL_AVAILABLE
    );
    protected static final ChangeProductCommand CHANGE_PRODUCT_COMMAND = ChangeProductCommand.of(
            CATEGORY_ID, PRODUCT_NAME, MONEY_2000, ProductStatus.RENTAL_STOPPED
    );

    protected static final Product SALE_PRODUCT = Product.of(
            PRODUCT_ID, ProductType.SALE_PRODUCT, CATEGORY_ID, PRODUCT_NAME, MONEY_1000, ProductStatus.SALE_AVAILABLE, NOW, NOW, null, 0L
    );
    protected static final Product RENTAL_PRODUCT = Product.of(
            PRODUCT_ID, ProductType.RENTAL_PRODUCT, CATEGORY_ID, PRODUCT_NAME, MONEY_1000, ProductStatus.RENTAL_AVAILABLE, NOW, NOW, null, 0L
    );
}
