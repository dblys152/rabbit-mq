package com.ys.product.application.service;

import com.ys.product.application.port.in.GetProductQuery;
import com.ys.product.application.port.out.LoadProductPort;
import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductQueryService implements GetProductQuery {
    private final LoadProductPort loadProductPort;

    @Override
    public Product getById(ProductId productId) {
        return loadProductPort.findById(productId);
    }
}
