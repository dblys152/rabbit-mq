package com.ys.product.adapter.in;

import com.ys.shared.utils.ApiResponseModel;
import com.ys.product.adapter.in.model.ProductModel;
import com.ys.product.application.port.in.GetProductQuery;
import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/products",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductQueryController {
    private final GetProductQuery getProductQuery;

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponseModel<ProductModel>> getAll(@PathVariable("productId") long productId) {
        Product product = getProductQuery.getById(ProductId.of(productId));

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseModel.success(
                HttpStatus.OK.value(), ProductModel.fromDomain(product)));
    }
}
