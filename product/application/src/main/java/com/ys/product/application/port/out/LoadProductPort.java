package com.ys.product.application.port.out;

import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductId;
import com.ys.product.domain.product.Products;

import java.util.List;

public interface LoadProductPort {
    Product findById(ProductId productId);
    Products findAllById(List<ProductId> ids);
}
