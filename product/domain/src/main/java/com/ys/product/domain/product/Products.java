package com.ys.product.domain.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Value(staticConstructor = "of")
public class Products {
    @Valid
    @NotNull
    List<Product> items;

    public boolean isEmpty() {
        return this.items == null || this.items.isEmpty();
    }

    public Products changeStatus(List<ChangeProductStatusCommand> commandList) {
        Map<ProductId, ChangeProductStatusCommand> commandMap = commandList.stream()
                .collect(Collectors.toMap(ChangeProductStatusCommand::getProductId, Function.identity()));

        List<Product> changedList = this.items.stream()
                .filter(i -> commandMap.containsKey(i.getProductId()))
                .map(i -> {
                    i.changeStatus(commandMap.get(i.getProductId()));
                    return i;
                })
                .toList();

        return Products.of(changedList);
    }
}
