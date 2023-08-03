package com.ys.rental.application.port.in;

import com.ys.rental.domain.DoReturnCommand;
import com.ys.rental.domain.Rental;

public interface DoReturnUseCase {

    Rental doReturn(DoReturnCommand command);
}
