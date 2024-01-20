package com.ys.product.application.port.in;

import com.ys.product.domain.product.ChangeProductCommand;
import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductId;

public interface ChangeProductUseCase {
    Product change(ProductId productId, ChangeProductCommand command);
}
