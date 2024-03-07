package com.ys.product.domain.product;

import com.ys.product.domain.product.fixture.SupportProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest extends SupportProductFixture {
    private Product saleProduct;
    private Product rentalProduct;

    @BeforeEach
    void setUp() {
        saleProduct = Product.of(
                PRODUCT_ID, ProductType.SALE_PRODUCT, ProductStatus.SALE_AVAILABLE, CATEGORY_ID, PRODUCT_NAME, MONEY_1000, NOW, NOW, null, 0L);
        rentalProduct = Product.of(
                PRODUCT_ID2, ProductType.RENTAL_PRODUCT, ProductStatus.RENTAL_AVAILABLE, CATEGORY_ID, PRODUCT_NAME, MONEY_1000, NOW, NOW, null, 0L);
    }

    @Test
    void 상품을_등록한다() {
        CreateProductCommand command = new CreateProductCommand(
                ProductType.RENTAL_PRODUCT, ProductStatus.RENTAL_AVAILABLE, CATEGORY_ID, PRODUCT_NAME, MONEY_1000);

        Product actual = Product.create(PRODUCT_ID, command);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getProductId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(command.getType()),
                () -> assertThat(actual.getCategoryId()).isEqualTo(command.getCategoryId()),
                () -> assertThat(actual.getName()).isEqualTo(command.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(command.getPrice()),
                () -> assertThat(actual.getStatus()).isEqualTo(command.getStatus())
        );
    }

    @Test
    void 상품_등록_시_상품_유형에_맞는_상태가_아니면_에러를_반환한다() {
        CreateProductCommand command = new CreateProductCommand(
                ProductType.RENTAL_PRODUCT, ProductStatus.SALE_AVAILABLE, CATEGORY_ID, PRODUCT_NAME, MONEY_1000);

        assertThatThrownBy(() -> Product.create(PRODUCT_ID, command)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품을_수정한다() {
        ChangeProductCommand command = new ChangeProductCommand(
                ProductStatus.SALE_AVAILABLE, CATEGORY_ID, PRODUCT_NAME, MONEY_2000);

        saleProduct.change(command);

        assertAll(
                () -> assertThat(saleProduct.getCategoryId()).isEqualTo(command.getCategoryId()),
                () -> assertThat(saleProduct.getName()).isEqualTo(command.getName()),
                () -> assertThat(saleProduct.getPrice()).isEqualTo(command.getPrice()),
                () -> assertThat(saleProduct.getStatus()).isEqualTo(command.getStatus())
        );
    }

    @Test
    void 상품_수정_시_상품_유형에_맞는_상태가_아니면_에러를_반환한다() {
        ChangeProductCommand command = new ChangeProductCommand(
                ProductStatus.RENTAL_STOPPED, CATEGORY_ID, PRODUCT_NAME, MONEY_1000);

        assertThatThrownBy(() -> saleProduct.change(command)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품을_삭제한다() {
        saleProduct.delete();

        assertThat(saleProduct.getDeletedAt()).isNotNull();
    }

    @Test
    void 상품_상태를_변경한다() {
        ChangeProductStatusCommand command = new ChangeProductStatusCommand(PRODUCT_ID, ProductStatus.UNDER_REPAIR);

        rentalProduct.changeStatus(command);

        assertThat(rentalProduct.getStatus()).isEqualTo(command.getStatus());
    }

    @Test
    void 상태_변경_시_상품_유형에_맞는_상태가_아니면_에러를_반환한다() {
        ChangeProductStatusCommand command = new ChangeProductStatusCommand(PRODUCT_ID, ProductStatus.SALE_AVAILABLE);

        assertThatThrownBy(() -> rentalProduct.changeStatus(command)).isInstanceOf(IllegalArgumentException.class);
    }
}