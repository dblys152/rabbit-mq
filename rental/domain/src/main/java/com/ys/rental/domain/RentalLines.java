package com.ys.rental.domain;

import com.ys.product.domain.product.Product;
import com.ys.product.domain.product.ProductId;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Embeddable
@Value(staticConstructor = "of")
public class RentalLines {

    @NotNull
    @Size(min = 1)
    @OneToMany()
    List<RentalLine> items;

    private RentalLines(List<RentalLine> items) {
        this.items = items;
        validationRedundancy();
    }

    private void validationRedundancy() {
        Set<ProductId> productIdSet = new HashSet<>();
        this.items.stream()
                .filter(r -> !productIdSet.add(r.getProductId()))
                .findFirst()
                .ifPresent(p -> {
                    throw new IllegalArgumentException("상품을 중복해서 넣을 수 없습니다.");
                });
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }
}
