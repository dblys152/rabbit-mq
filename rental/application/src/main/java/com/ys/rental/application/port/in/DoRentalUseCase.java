package com.ys.rental.application.port.in;

import com.ys.rental.domain.DoRentalCommand;
import com.ys.rental.domain.Rental;

public interface DoRentalUseCase {

    Rental doRental(DoRentalCommand command);
}
