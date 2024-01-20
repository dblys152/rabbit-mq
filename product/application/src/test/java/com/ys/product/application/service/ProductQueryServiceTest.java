package com.ys.product.application.service;

import com.ys.product.application.port.out.LoadProductPort;
import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProductQueryServiceTest {
    private static final ProductId PRODUCT_ID = ProductId.of(99999999999L);

    @InjectMocks
    private ProductQueryService sut;

    @Mock
    private LoadProductPort loadProductPort;

    @Test
    void 상품을_조회한다() {
        given(loadProductPort.findById(PRODUCT_ID)).willReturn(mock(Product.class));

        Product actual = loadProductPort.findById(PRODUCT_ID);

        assertThat(actual).isNotNull();
        then(loadProductPort).should().findById(PRODUCT_ID);
    }
}