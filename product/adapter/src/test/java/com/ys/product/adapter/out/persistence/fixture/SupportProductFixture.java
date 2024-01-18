package com.ys.product.adapter.out.persistence.fixture;

import com.ys.product.domain.product.Money;
import com.ys.product.domain.product.ProductId;
import com.ys.product.refs.category.domain.CategoryId;

import java.time.LocalDateTime;

public class SupportProductFixture {
    protected static final LocalDateTime NOW = LocalDateTime.now();
    protected static final ProductId PRODUCT_ID = ProductId.of(99999999999L);
    protected static final ProductId PRODUCT_ID2 = ProductId.of(99999999998L);
    protected static final CategoryId CATEGORY_ID = CategoryId.of(123);
    protected static final String PRODUCT_NAME = "PRODUCT_NAME";
    protected static final Money MONEY_1000 = Money.of(1000);
    protected static final Money MONEY_2000 = Money.of(2000);
}
