package com.ys.product.application.service;

import com.github.f4b6a3.tsid.TsidCreator;
import com.ys.product.application.port.in.*;
import com.ys.product.application.port.out.LoadProductPort;
import com.ys.product.application.port.out.RecordProductPort;
import com.ys.product.domain.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductCommandService implements
        RegisterProductUseCase, ChangeProductUseCase, ChangeProductStatusUseCase, ChangeProductStatusBulkUseCase, DeleteProductUseCase {
    private final RecordProductPort recordProductPort;
    private final LoadProductPort loadProductPort;

    @Override
    public Product register(CreateProductCommand command) {
        ProductId productId = ProductId.of(TsidCreator.getTsid().toLong());

        Product product = Product.create(productId, command);
        Product savedProduct = recordProductPort.save(product);

        return savedProduct;
    }

    @Override
    public Product change(ProductId productId, ChangeProductCommand command) {
        Product product = loadProductPort.findById(productId);

        product.change(command);
        Product savedProduct = recordProductPort.save(product);

        return savedProduct;
    }

    @Override
    public Product changeStatus(ProductId productId, ChangeProductStatusCommand command) {
        Product product = loadProductPort.findById(productId);

        product.changeStatus(command);
        Product savedProduct = recordProductPort.save(product);

        return savedProduct;
    }

    @Override
    public Products changeStatusBulk(List<ChangeProductStatusCommand> commandList) {
        Products products = loadProductPort.findAllById(commandList.stream()
                .map(c -> c.getProductId())
                .toList());

        Products changedProducts = products.changeStatus(commandList);

        if (!changedProducts.isEmpty()) {
            Products savedProducts = recordProductPort.saveAll(changedProducts);
            return savedProducts;
        }

        return changedProducts;
    }

    @Override
    public void delete(ProductId productId) {
        Product product = loadProductPort.findById(productId);

        product.delete();
        recordProductPort.save(product);
    }
}
