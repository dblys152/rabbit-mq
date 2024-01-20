package com.ys.product.adapter.out;

import com.ys.product.adapter.out.persistence.ProductRepository;
import com.ys.product.application.port.out.LoadProductPort;
import com.ys.product.application.port.out.RecordProductPort;
import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements RecordProductPort, LoadProductPort {
    private final ProductRepository repository;

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product findById(ProductId productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException());
    }
}
