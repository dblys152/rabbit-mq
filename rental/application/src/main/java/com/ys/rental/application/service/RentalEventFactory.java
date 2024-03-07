package com.ys.rental.application.service;

import com.ys.shared.utils.EventFactory;
import com.ys.rental.domain.Rental;
import com.ys.rental.domain.RentalLines;
import com.ys.rental.domain.RentalPeriod;
import com.ys.rental.domain.event.RentalEvent;
import org.springframework.stereotype.Component;

@Component
public class RentalEventFactory implements EventFactory<Rental, RentalEvent> {
    @Override
    public RentalEvent create(Rental domain) {
        RentalLines rentalLines = domain.getRentalLines();
        RentalPeriod rentalPeriod = domain.getRentalPeriod();
        return new RentalEvent(
                domain.getRentalId().get(),
                domain.getUserId().get(),
                domain.getStatus(),
                rentalLines.getItems().stream()
                        .map(rl -> new RentalEvent.RentalLineEvent(rl.getProductId().get(), rl.getPrice().getValue(), rl.getQuantity()))
                        .toList(),
                rentalPeriod.getStartedAt(),
                rentalPeriod.getEndedAt(),
                domain.getReturnedAt()
        );
    }
}
