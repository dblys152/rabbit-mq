package com.ys.rental.application.port.out;


import com.ys.rental.domain.Rental;

public interface RecordRentalPort {

    Rental save(Rental rental);
}
