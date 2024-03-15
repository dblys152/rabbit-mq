package com.ys.product.application.service;

import com.ys.product.application.port.in.ChangeProductStatusBulkUseCase;
import com.ys.product.refs.rental.domain.RentalEvent;
import com.ys.product.refs.rental.domain.RentalStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ChangeProductStatusBulkProcessorTest {
    @InjectMocks
    private ChangeProductStatusBulkProcessor sut;

    @Mock
    private ChangeProductStatusBulkUseCase changeProductStatusBulkUseCase;

    @Test
    void 상품_상태_일괄_변경_서비스를_호출한다() {
        RentalEvent rentalEvent = mock(RentalEvent.class);
        RentalEvent.RentalLineEvent rentalLineEvent = mock(RentalEvent.RentalLineEvent.class);
        RentalEvent.RentalLineEvent rentalLineEvent2 = mock(RentalEvent.RentalLineEvent.class);
        List<RentalEvent.RentalLineEvent> rentalLineEventList = List.of(rentalLineEvent, rentalLineEvent2);
        given(rentalEvent.getRentalLineList()).willReturn(rentalLineEventList);
        given(rentalEvent.getStatus()).willReturn(RentalStatus.RETURNED);

        sut.accept(rentalEvent);

        then(changeProductStatusBulkUseCase).should().changeStatusBulk(any());
    }
}