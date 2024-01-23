package com.ys.rental.adapter.out;

import com.ys.rental.adapter.out.persistence.RentalEntity;
import com.ys.rental.adapter.out.persistence.RentalLineEntity;
import com.ys.rental.adapter.out.persistence.RentalLineEntityRepository;
import com.ys.rental.application.port.out.RecordRentalLinePort;
import com.ys.rental.domain.Rental;
import com.ys.rental.domain.RentalLine;
import com.ys.rental.domain.RentalLines;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentalLinePersistenceAdapter implements RecordRentalLinePort {
    private final RentalLineEntityRepository repository;

    @Override
    public void saveAll(Rental rental) {
        RentalEntity rentalEntity = RentalEntity.fromDomain(rental);
        repository.saveAll(rentalEntity.getRentalLineList());
    }
}
