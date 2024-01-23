package com.ys.rental.application.service;

import com.ys.infrastructure.exception.BadRequestException;
import com.ys.infrastructure.utils.CommandFactory;
import com.ys.rental.application.port.in.DoRentalRequest;
import com.ys.rental.domain.*;
import com.ys.rental.refs.product.domain.ProductId;
import com.ys.rental.refs.user.domain.UserId;
import org.springframework.stereotype.Component;

@Component
public class DoRentalCommandFactory implements CommandFactory<DoRentalRequest, DoRentalCommand> {
    @Override
    public DoRentalCommand create(DoRentalRequest request) {
        try {
            return new DoRentalCommand(
                    UserId.of(request.getUserId()),
                    RentalLines.of(request.getRentalLineList().stream()
                            .map(r -> RentalLine.create(ProductId.of(r.getProductId()), Money.of(r.getPrice()), r.getQuantity()))
                            .toList()),
                    RentalPeriod.of(request.getStartedAt(), request.getEndedAt())
            );
        } catch (IllegalArgumentException | IllegalStateException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
}
