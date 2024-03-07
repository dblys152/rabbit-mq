package com.ys.product.adapter.in;

import com.ys.shared.utils.ApiResponseModel;
import com.ys.shared.utils.CommandFactory;
import com.ys.product.adapter.in.model.ProductModel;
import com.ys.product.application.port.in.ChangeProductStatusUseCase;
import com.ys.product.application.port.in.ChangeProductUseCase;
import com.ys.product.application.port.in.DeleteProductUseCase;
import com.ys.product.application.port.in.RegisterProductUseCase;
import com.ys.product.application.port.in.model.ChangeProductRequest;
import com.ys.product.application.port.in.model.ChangeProductStatusRequest;
import com.ys.product.application.port.in.model.RegisterProductRequest;
import com.ys.product.domain.product.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/events",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductCommandController {
    private final CommandFactory<RegisterProductRequest, CreateProductCommand> createProductCommandFactory;
    private final RegisterProductUseCase registerProductUseCase;
    private final CommandFactory<ChangeProductRequest, ChangeProductCommand> changeProductCommandFactory;
    private final ChangeProductUseCase changeProductUseCase;
    private final ChangeProductStatusUseCase changeProductStatusUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @PostMapping("")
    public ResponseEntity<ApiResponseModel<ProductModel>> register(@Valid @RequestBody RegisterProductRequest request) {
        CreateProductCommand command = createProductCommandFactory.create(request);

        Product product = registerProductUseCase.register(command);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseModel.success(
                HttpStatus.CREATED.value(), ProductModel.fromDomain(product)));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponseModel<ProductModel>> change(
            @PathVariable("productId") long productId,
            @Valid @RequestBody ChangeProductRequest request) {
        ChangeProductCommand command = changeProductCommandFactory.create(request);

        Product product = changeProductUseCase.change(ProductId.of(productId), command);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseModel.success(
                HttpStatus.OK.value(), ProductModel.fromDomain(product)));
    }

    @PatchMapping("/{productId}/status")
    public ResponseEntity<ApiResponseModel<ProductModel>> changeStatus(
            @PathVariable("productId") long productId,
            @Valid @RequestBody ChangeProductStatusRequest request) {
        Product product = changeProductStatusUseCase.changeStatus(
                ProductId.of(productId), new ChangeProductStatusCommand(ProductId.of(productId), request.getStatus()));

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseModel.success(
                HttpStatus.OK.value(), ProductModel.fromDomain(product)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> delete(@PathVariable("productId") long productId) {
        deleteProductUseCase.delete(ProductId.of(productId));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
