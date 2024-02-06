package com.ys.product.application.port.in;

import com.ys.product.domain.product.ChangeProductStatusCommand;
import com.ys.product.domain.product.Products;

import java.util.List;

public interface ChangeProductStatusBulkUseCase {
    Products changeStatusBulk(List<ChangeProductStatusCommand> commandList);
}
