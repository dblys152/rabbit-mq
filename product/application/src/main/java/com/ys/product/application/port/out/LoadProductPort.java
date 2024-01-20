package com.ys.product.application.port.out;

import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductId;

public interface LoadProductPort {
    Product findById(ProductId productId);
}
