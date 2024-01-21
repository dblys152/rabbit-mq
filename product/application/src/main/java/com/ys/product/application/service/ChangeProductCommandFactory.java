package com.ys.product.application.service;

import com.ys.infrastructure.exception.BadRequestException;
import com.ys.infrastructure.utils.CommandFactory;
import com.ys.product.application.port.in.model.ChangeProductRequest;
import com.ys.product.domain.product.ChangeProductCommand;
import com.ys.product.domain.product.Money;
import com.ys.product.refs.category.domain.CategoryId;
import org.springframework.stereotype.Component;

@Component
public class ChangeProductCommandFactory implements CommandFactory<ChangeProductRequest, ChangeProductCommand> {
    @Override
    public ChangeProductCommand create(ChangeProductRequest request) {
        try {
            return new ChangeProductCommand(
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
