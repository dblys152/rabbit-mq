package com.ys.event_store.application.service;

import com.ys.event_store.application.port.out.RecordEventPort;
import com.ys.event_store.domain.CreateEventCommand;
import com.ys.event_store.domain.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class EventCommandServiceTest {

    @InjectMocks
    private EventCommandService sut;

    @Mock
    private RecordEventPort recordEventPort;

    @Test
    void 이벤트를_등록한다() {
        Event actual = sut.create(mock(CreateEventCommand.class));

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> then(recordEventPort).should().insert(any(Event.class))
        );
    }
}