package com.ys.product.adapter.out.persistence;

import com.ys.product.adapter.config.DataJpaConfig;
import com.ys.product.adapter.out.persistence.fixture.SupportProductFixture;
import com.ys.product.domain.product.CreateProductCommand;
import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductStatus;
import com.ys.product.domain.product.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = DataJpaConfig.class)
class ProductRepositoryTest extends SupportProductFixture {
    @Autowired
    private ProductRepository repository;

    @Test
    void save() {
        CreateProductCommand command = CreateProductCommand.of(
                ProductType.RENTAL_PRODUCT, CATEGORY_ID, PRODUCT_NAME, MONEY_1000, ProductStatus.RENTAL_AVAILABLE);
        Product product = Product.create(PRODUCT_ID, command);

        Product actual = repository.save(product);

        assertThat(actual).isNotNull();
    }

    @Test
    void findById() {
        CreateProductCommand command = CreateProductCommand.of(
                ProductType.RENTAL_PRODUCT, CATEGORY_ID, PRODUCT_NAME, MONEY_1000, ProductStatus.RENTAL_AVAILABLE);
        Product product = Product.create(PRODUCT_ID, command);
        Product savedProduct = repository.save(product);

        Optional<Product> actual = repository.findById(savedProduct.getProductId());

        assertThat(actual).isPresent();
    }
}