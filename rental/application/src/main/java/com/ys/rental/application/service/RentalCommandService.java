package com.ys.rental.application.service;

import com.github.f4b6a3.tsid.TsidCreator;
import com.ys.infrastructure.message.DomainEventPublisher;
import com.ys.rental.application.port.in.DoCancelUseCase;
import com.ys.rental.application.port.in.DoRentalUseCase;
import com.ys.rental.application.port.in.DoReturnUseCase;
import com.ys.rental.application.port.out.LoadRentalPort;
import com.ys.rental.application.port.out.RecordRentalLinePort;
import com.ys.rental.application.port.out.RecordRentalPort;
import com.ys.rental.domain.DoRentalCommand;
import com.ys.rental.domain.Rental;
import com.ys.rental.domain.RentalEventType;
import com.ys.rental.domain.RentalId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class RentalCommandService implements DoRentalUseCase, DoCancelUseCase, DoReturnUseCase {
    private final DomainEventPublisher<Rental> rentalDomainEventPublisher;
    private final RecordRentalPort recordRentalPort;
    private final RecordRentalLinePort recordRentalLinePort;
    private final LoadRentalPort loadRentalPort;

    @Override
    public Rental doRental(DoRentalCommand command) {
        RentalId rentalId = RentalId.of(TsidCreator.getTsid().toLong());

        Rental rental = Rental.create(rentalId, command);
        Rental savedRental = recordRentalPort.save(rental);
        recordRentalLinePort.saveAll(savedRental);

        rentalDomainEventPublisher.publish(RentalEventType.DO_RENTAL_EVENT.name(), savedRental, savedRental.getCreatedAt());

        return savedRental;
    }

    @Override
    public Rental doCancel(RentalId rentalId) {
        Rental rental = loadRentalPort.findById(rentalId);

        rental.doCancel();
        Rental savedRental = recordRentalPort.save(rental);

        rentalDomainEventPublisher.publish(RentalEventType.DO_CANCEL_RENTAL_EVENT.name(), savedRental, savedRental.getModifiedAt());

        return savedRental;
    }

    @Override
    public Rental doReturn(RentalId rentalId) {
        Rental rental = loadRentalPort.findById(rentalId);

        rental.doReturn();
        Rental savedRental = recordRentalPort.save(rental);

        rentalDomainEventPublisher.publish(RentalEventType.DO_RETURN_RENTAL_EVENT.name(), savedRental, savedRental.getReturnedAt());

        return savedRental;
    }
}
