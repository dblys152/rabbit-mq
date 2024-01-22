package com.ys.rental.domain;

import com.ys.rental.refs.product.domain.ProductId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Value(staticConstructor = "of")
public class RentalLines {
    @NotNull
    @Size(min = 1)
    List<RentalLine> items;

    private RentalLines(List<RentalLine> items) {
        this.items = items;
        validateRedundancy();
    }

    private void validateRedundancy() {
        Set<ProductId> productIdSet = new HashSet<>();
        this.items.stream()
                .filter(r -> !productIdSet.add(r.getProductId()))
                .findFirst()
                .ifPresent(p -> {
                    throw new IllegalArgumentException("상품이 중복됩니다.");
                });
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }
}
