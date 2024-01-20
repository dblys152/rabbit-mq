package com.ys.product.application.port.in;

import com.ys.product.domain.product.ProductId;

public interface DeleteProductUseCase {
    void delete(ProductId productId);
}
