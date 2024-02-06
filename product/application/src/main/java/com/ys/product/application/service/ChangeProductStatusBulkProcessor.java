package com.ys.product.application.service;

import com.ys.product.application.port.in.ChangeProductStatusBulkUseCase;
import com.ys.product.domain.product.ChangeProductStatusCommand;
import com.ys.product.domain.product.ProductId;
import com.ys.product.domain.product.ProductStatus;
import com.ys.product.refs.rental.domain.RentalEvent;
import com.ys.product.refs.rental.domain.RentalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class ChangeProductStatusBulkProcessor implements Consumer<RentalEvent> {
    private ChangeProductStatusBulkUseCase service;

    @Override
    public void accept(RentalEvent rentalEvent) {
        List<RentalEvent.RentalLineEvent> rentalLineList = rentalEvent.getRentalLineList();
        service.changeStatusBulk(rentalLineList.stream()
                .map(rl -> new ChangeProductStatusCommand(ProductId.of(rl.getProductId()), getProductStatus(rentalEvent.getStatus())))
                .toList());
    }

    private ProductStatus getProductStatus(RentalStatus rentalStatus) {
        if (rentalStatus == null) {
            throw new IllegalArgumentException("RentalStatus cannot be null");
        }

        return switch (rentalStatus) {
            case RENTED -> ProductStatus.RENTED;
            case CANCELED, RETURNED -> ProductStatus.RENTAL_AVAILABLE;
        };
    }
}
