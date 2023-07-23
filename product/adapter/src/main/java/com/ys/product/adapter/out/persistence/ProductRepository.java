package com.ys.product.adapter.out.persistence;

import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {

}
