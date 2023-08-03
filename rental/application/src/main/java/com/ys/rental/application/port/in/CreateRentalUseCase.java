package com.ys.rental.application.port.in;

import com.ys.rental.domain.CreateRentalCommand;
import com.ys.rental.domain.Rental;

public interface CreateRentalUseCase {

    Rental create(CreateRentalCommand command);
}
