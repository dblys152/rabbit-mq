package com.ys.product.application.port.in;

import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductId;

public interface GetProductQuery {
    Product getById(ProductId productId);
}
