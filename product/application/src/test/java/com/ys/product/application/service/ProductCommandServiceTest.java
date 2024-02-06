package com.ys.product.application.service;

import com.ys.product.application.port.out.LoadProductPort;
import com.ys.product.application.port.out.RecordProductPort;
import com.ys.product.domain.product.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductCommandServiceTest {
    private static final ProductId PRODUCT_ID = ProductId.of(99999999999L);
    private static final ProductId PRODUCT_ID2 = ProductId.of(99999999998L);

    @InjectMocks
    private ProductCommandService sut;

    @Mock
    private RecordProductPort recordProductPort;
    @Mock
    private LoadProductPort loadProductPort;

    @Test
    void 상품을_등록한다() {
        CreateProductCommand command = mock(CreateProductCommand.class);

        when(recordProductPort.save(any(Product.class))).thenReturn(mock(Product.class));
        Product actual = sut.register(command);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> then(recordProductPort).should().save(any(Product.class))
        );
    }

    @Test
    void 상품을_변경한다() {
        ChangeProductCommand command = mock(ChangeProductCommand.class);
        Product mockProduct = mock(Product.class);
        given(loadProductPort.findById(PRODUCT_ID)).willReturn(mockProduct);

        when(recordProductPort.save(mockProduct)).thenReturn(mock(Product.class));
        Product actual = sut.change(PRODUCT_ID, command);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> then(loadProductPort).should().findById(PRODUCT_ID),
                () -> then(mockProduct).should().change(command),
                () -> then(recordProductPort).should().save(mockProduct)
        );
    }

    @Test
    void 상품_상태를_변경한다() {
        ChangeProductStatusCommand command = mock(ChangeProductStatusCommand.class);
        Product mockProduct = mock(Product.class);
        given(loadProductPort.findById(PRODUCT_ID)).willReturn(mockProduct);

        when(recordProductPort.save(mockProduct)).thenReturn(mock(Product.class));
        Product actual = sut.changeStatus(PRODUCT_ID, command);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> then(loadProductPort).should().findById(PRODUCT_ID),
                () -> then(mockProduct).should().changeStatus(command),
                () -> then(recordProductPort).should().save(mockProduct)
        );
    }

    @Test
    void 상품_상태를_일괄_변경한다() {
        ChangeProductStatusCommand command = getMockChangeProductStatusCommand(PRODUCT_ID);
        ChangeProductStatusCommand command2 = getMockChangeProductStatusCommand(PRODUCT_ID2);
        List<ChangeProductStatusCommand> commandList = List.of(command, command2);

        List<ProductId> productIdList = List.of(PRODUCT_ID, PRODUCT_ID2);
        Products mockProducts = mock(Products.class);
        given(loadProductPort.findAllById(productIdList)).willReturn(mockProducts);

        Products changedProducts = mock(Products.class);
        when(mockProducts.changeStatus(commandList)).thenReturn(changedProducts);
        when(recordProductPort.saveAll(changedProducts)).thenReturn(mock(Products.class));

        Products actual = sut.changeStatusBulk(commandList);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> then(loadProductPort).should().findAllById(productIdList),
                () -> then(mockProducts).should().changeStatus(commandList),
                () -> then(recordProductPort).should().saveAll(changedProducts)
        );
    }

    private static ChangeProductStatusCommand getMockChangeProductStatusCommand(ProductId productId) {
        ChangeProductStatusCommand command = mock(ChangeProductStatusCommand.class);
        given(command.getProductId()).willReturn(productId);
        return command;
    }

    @Test
    void 상품을_삭제한다() {
        Product mockProduct = mock(Product.class);
        given(loadProductPort.findById(PRODUCT_ID)).willReturn(mockProduct);

        sut.delete(PRODUCT_ID);

        assertAll(
                () -> then(loadProductPort).should().findById(PRODUCT_ID),
                () -> then(mockProduct).should().delete(),
                () -> then(recordProductPort).should().save(mockProduct)
        );
    }
}