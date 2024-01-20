package com.ys.product.application.port.in;

import com.ys.product.domain.product.ChangeProductStatusCommand;
import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductId;

public interface ChangeProductStatusUseCase {
    Product changeStatus(ProductId productId, ChangeProductStatusCommand command);
}
