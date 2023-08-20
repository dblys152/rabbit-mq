package com.ys.event_store.application.service;

import com.ys.event_store.application.port.out.LoadEventPort;
import com.ys.event_store.domain.Events;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class EventQueryServiceTest {

    private static final LocalDate NOW_DATE = LocalDate.now();
    private static final String ANY_TYPE = "PRODUCT_RENTED_EVENT";
    private static final LocalDateTime START_AT = NOW_DATE.atStartOfDay();
    private static final LocalDateTime END_AT = NOW_DATE.atTime(23, 59, 59, 999);

    @InjectMocks
    private EventQueryService sut;

    @Mock
    private LoadEventPort loadEventPort;

    @Test
    void 특정_타입과_기간으로_이벤트_목록을_조회한다() {
        given(loadEventPort.selectAllByTypeAndOccurredAtBetween(ANY_TYPE, START_AT, END_AT))
                .willReturn(mock(Events.class));

        Events actual = sut.getAllByTypeAndOccurredAtBetween(ANY_TYPE, START_AT, END_AT);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> then(loadEventPort).should().selectAllByTypeAndOccurredAtBetween(ANY_TYPE, START_AT, END_AT)
        );
    }
}