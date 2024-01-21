package com.ys.product.application.service;

import com.ys.infrastructure.exception.BadRequestException;
import com.ys.infrastructure.utils.CommandFactory;
import com.ys.product.application.port.in.model.RegisterProductRequest;
import com.ys.product.domain.product.CreateProductCommand;
import com.ys.product.domain.product.Money;
import com.ys.product.refs.category.domain.CategoryId;
import org.springframework.stereotype.Component;

@Component
public class CreateProductCommandFactory implements CommandFactory<RegisterProductRequest, CreateProductCommand> {
    @Override
    public CreateProductCommand create(RegisterProductRequest request) {
        try {
            return new CreateProductCommand(
                    request.getType(),
                    request.getStatus(),
                    CategoryId.of(request.getCategoryId()),
                    request.getName(),
                    Money.of(request.getPrice())
            );
        } catch (IllegalArgumentException | IllegalStateException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}
