package com.ys.rental.application.port.in;

import com.ys.rental.domain.Rental;
import com.ys.rental.domain.RentalId;

public interface DoReturnUseCase {
    Rental doReturn(RentalId rentalId);
}
