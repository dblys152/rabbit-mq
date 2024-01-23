package com.ys.rental.adapter.out;

import com.ys.rental.adapter.out.persistence.RentalEntity;
import com.ys.rental.adapter.out.persistence.RentalEntityRepository;
import com.ys.rental.adapter.out.persistence.RentalEntityRepositorySupport;
import com.ys.rental.application.port.out.LoadRentalPort;
import com.ys.rental.application.port.out.RecordRentalPort;
import com.ys.rental.domain.Rental;
import com.ys.rental.domain.RentalId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class RentalPersistenceAdapter implements RecordRentalPort, LoadRentalPort {
    private final RentalEntityRepository repository;
    private final RentalEntityRepositorySupport repositorySupport;

    @Override
    public Rental save(Rental rental) {
        RentalEntity entity = repository.save(RentalEntity.fromDomain(rental));
        return entity.toDomain();
    }

    @Override
    public Rental findById(RentalId rentalId) {
        return repositorySupport.findById(rentalId.get())
                .orElseThrow(NoSuchElementException::new)
                .toDomain();
    }
}
