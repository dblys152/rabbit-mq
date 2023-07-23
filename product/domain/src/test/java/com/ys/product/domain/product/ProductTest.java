package com.ys.product.domain.product;

import com.ys.product.domain.product.fixture.SupportProductFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest extends SupportProductFixture {

    @Test
    void 상품을_등록한다() {
        CreateProductCommand command = CREATE_PRODUCT_COMMAND;
        Product actual = Product.create(command);

        assertThat(actual).isNotNull();
    }

    @Test
    void 상품_등록_시_상품_유형에_맞는_상태가_아니면_에러를_반환한다() {
        CreateProductCommand command = CreateProductCommand.of(
                ProductType.RENTAL_PRODUCT, CATEGORY_ID, PRODUCT_NAME, MONEY_1000, ProductStatus.SALE_AVAILABLE);

        assertThatThrownBy(() -> Product.create(command)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품을_수정한다() {
        Product product = RENTAL_PRODUCT;
        ChangeProductCommand command = CHANGE_PRODUCT_COMMAND;
        product.change(command);

        assertAll(
                () -> assertThat(product.getCategoryId()).isEqualTo(command.getCategoryId()),
                () -> assertThat(product.getName()).isEqualTo(command.getName()),
                () -> assertThat(product.getPrice()).isEqualTo(command.getPrice()),
                () -> assertThat(product.getStatus()).isEqualTo(command.getStatus())
        );
    }

    @Test
    void 상품_수정_시_상품_유형에_맞는_상태가_아니면_에러를_반환한다() {
        Product product = RENTAL_PRODUCT;
        ChangeProductCommand command = ChangeProductCommand.of(
                CATEGORY_ID, PRODUCT_NAME, MONEY_1000, ProductStatus.SALE_AVAILABLE);

        assertThatThrownBy(() -> product.change(command)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품을_삭제한다() {
        Product product = RENTAL_PRODUCT;
        product.delete();

        assertThat(product.getDeletedAt()).isNotNull();
    }

    @Test
    void 상품_상태를_변경한다() {
        Product product = RENTAL_PRODUCT;
        ChangeStatusCommand command = ChangeStatusCommand.of(ProductStatus.UNDER_REPAIR);
        product.changeStatus(command);

        assertThat(product.getStatus()).isEqualTo(command.getStatus());
    }

    @Test
    void 상태_변경_시_상품_유형에_맞는_상태가_아니면_에러를_반환한다() {
        Product product = RENTAL_PRODUCT;
        ChangeStatusCommand command = ChangeStatusCommand.of(ProductStatus.SALE_AVAILABLE);

        assertThatThrownBy(() -> product.changeStatus(command)).isInstanceOf(IllegalArgumentException.class);
    }
}