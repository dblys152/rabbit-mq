package com.ys.product.application.port.out;

import com.ys.product.domain.product.Product;

public interface RecordProductPort {
    Product save(Product product);
}
