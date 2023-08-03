package com.ys.rental.application.port.out;

import com.ys.rental.domain.RentalId;

public interface LoadRentalPort {

    RentalId nextId();
}
