package com.ys.rental.application.service;

import com.ys.infrastructure.message.DomainEventPublisher;
import com.ys.rental.application.port.out.LoadRentalPort;
import com.ys.rental.application.port.out.RecordRentalLinePort;
import com.ys.rental.application.port.out.RecordRentalPort;
import com.ys.rental.domain.DoRentalCommand;
import com.ys.rental.domain.Rental;
import com.ys.rental.domain.RentalEventType;
import com.ys.rental.domain.RentalId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalCommandServiceTest {
    private static final RentalId RENTAL_ID = RentalId.of(123L);

    @InjectMocks
    private RentalCommandService sut;

    @Mock
    private RecordRentalPort recordRentalPort;
    @Mock
    private RecordRentalLinePort recordRentalLinePort;
    @Mock
    private DomainEventPublisher<Rental> rentalDomainEventPublisher;
    @Mock
    private LoadRentalPort loadRentalPort;

    @Test
    void 상품을_대여한다() {
        Rental mockSavedRental = mock(Rental.class);

        when(recordRentalPort.save(any(Rental.class))).thenReturn(mockSavedRental);
        Rental actual = sut.doRental(mock(DoRentalCommand.class));

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> then(recordRentalPort).should().save(any(Rental.class)),
                () -> then(recordRentalLinePort).should().saveAll(mockSavedRental),
                () -> then(rentalDomainEventPublisher).should().publish(
                        RentalEventType.DO_RENTAL_EVENT.name(), mockSavedRental, mockSavedRental.getCreatedAt())
        );
    }

    @Test
    void 대여_취소한다() {
        Rental mockRental = mock(Rental.class);
        given(loadRentalPort.findById(RENTAL_ID)).willReturn(mockRental);

        Rental mockSavedRental = mock(Rental.class);
        when(recordRentalPort.save(mockRental)).thenReturn(mockSavedRental);
        Rental actual = sut.doCancel(RENTAL_ID);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> then(loadRentalPort).should().findById(RENTAL_ID),
                () -> then(mockRental).should().doCancel(),
                () -> then(recordRentalPort).should().save(mockRental),
                () -> then(rentalDomainEventPublisher).should().publish(
                        RentalEventType.DO_CANCEL_RENTAL_EVENT.name(), mockSavedRental, mockSavedRental.getModifiedAt())
        );
    }

    @Test
    void 대여_반납한다() {
        Rental mockRental = mock(Rental.class);
        given(loadRentalPort.findById(RENTAL_ID)).willReturn(mockRental);

        Rental mockSavedRental = mock(Rental.class);
        when(recordRentalPort.save(mockRental)).thenReturn(mockSavedRental);
        Rental actual = sut.doReturn(RENTAL_ID);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> then(loadRentalPort).should().findById(RENTAL_ID),
                () -> then(mockRental).should().doReturn(),
                () -> then(recordRentalPort).should().save(mockRental),
                () -> then(rentalDomainEventPublisher).should().publish(
                        RentalEventType.DO_RETURN_RENTAL_EVENT.name(), mockSavedRental, mockSavedRental.getReturnedAt())
        );
    }
}