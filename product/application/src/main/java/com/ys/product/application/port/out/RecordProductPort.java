package com.ys.product.application.port.out;

import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.Products;

public interface RecordProductPort {
    Product save(Product product);
    Products saveAll(Products products);
}
