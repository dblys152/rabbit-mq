package com.ys.product.application.port.in;

import com.ys.product.domain.product.CreateProductCommand;
import com.ys.product.domain.product.Product;

public interface RegisterProductUseCase {
    Product register(CreateProductCommand command);
}
