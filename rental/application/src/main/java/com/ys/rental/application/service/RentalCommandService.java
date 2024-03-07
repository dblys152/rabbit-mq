package com.ys.rental.application.service;

import com.github.f4b6a3.tsid.TsidCreator;
import com.ys.shared.event.DomainEventPublisher;
import com.ys.shared.utils.EventFactory;
import com.ys.rental.application.port.in.DoCancelUseCase;
import com.ys.rental.application.port.in.DoRentalUseCase;
import com.ys.rental.application.port.in.DoReturnUseCase;
import com.ys.rental.application.port.out.LoadRentalPort;
import com.ys.rental.application.port.out.RecordRentalLinePort;
import com.ys.rental.application.port.out.RecordRentalPort;
import com.ys.rental.domain.DoRentalCommand;
import com.ys.rental.domain.Rental;
import com.ys.rental.domain.RentalId;
import com.ys.rental.domain.event.RentalEvent;
import com.ys.rental.domain.event.RentalEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class RentalCommandService implements DoRentalUseCase, DoCancelUseCase, DoReturnUseCase {
    private final RecordRentalPort recordRentalPort;
    private final RecordRentalLinePort recordRentalLinePort;
    private final LoadRentalPort loadRentalPort;
    private final EventFactory<Rental, RentalEvent> eventFactory;
    private final DomainEventPublisher<RentalEvent> domainEventPublisher;

    @Override
    public Rental doRental(DoRentalCommand command) {
        RentalId rentalId = RentalId.of(TsidCreator.getTsid().toLong());

        Rental rental = Rental.create(rentalId, command);
        Rental savedRental = recordRentalPort.save(rental);
        recordRentalLinePort.saveAll(savedRental);

        domainEventPublisher.publish(
                RentalEventType.DO_RENTAL_EVENT.name(), eventFactory.create(savedRental), savedRental.getCreatedAt());

        return savedRental;
    }

    @Override
    public Rental doCancel(RentalId rentalId) {
        Rental rental = loadRentalPort.findById(rentalId);

        rental.doCancel();
        Rental savedRental = recordRentalPort.save(rental);

        domainEventPublisher.publish(
                RentalEventType.DO_CANCEL_RENTAL_EVENT.name(), eventFactory.create(savedRental), savedRental.getModifiedAt());

        return savedRental;
    }

    @Override
    public Rental doReturn(RentalId rentalId) {
        Rental rental = loadRentalPort.findById(rentalId);

        rental.doReturn();
        Rental savedRental = recordRentalPort.save(rental);

        domainEventPublisher.publish(
                RentalEventType.DO_RETURN_RENTAL_EVENT.name(), eventFactory.create(savedRental), savedRental.getReturnedAt());

        return savedRental;
    }
}
