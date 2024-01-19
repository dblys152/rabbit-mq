package com.ys.event_store.application.service;

import com.github.f4b6a3.tsid.TsidCreator;
import com.ys.event_store.application.port.in.CreateEventUseCase;
import com.ys.event_store.application.port.out.RecordEventPort;
import com.ys.event_store.domain.CreateEventCommand;
import com.ys.event_store.domain.Event;
import com.ys.event_store.domain.EventId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class EventCommandService implements CreateEventUseCase {
    private final RecordEventPort recordEventPort;

    @Override
    public Event create(CreateEventCommand command) {
        EventId eventId = EventId.of(TsidCreator.getTsid().toLong());

        Event event = Event.create(eventId, command);
        recordEventPort.insert(event);

        return event;
    }
}
