package com.ys.product.adapter.out.persistence;

import com.ys.product.adapter.config.DataJpaConfig;
import com.ys.product.adapter.out.persistence.fixture.SupportProductFixture;
import com.ys.product.domain.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = DataJpaConfig.class)
class ProductRepositoryTest extends SupportProductFixture {

    @Autowired
    private ProductRepository repository;

    @Test
    void save() {
        Product product = Product.create(CREATE_PRODUCT_COMMAND);

        Product actual = repository.save(product);

        assertThat(actual).isNotNull();
        assertThat(actual.getProductId()).isNotNull();
    }
}