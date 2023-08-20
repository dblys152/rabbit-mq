package com.ys.event_store.application.port.in;

import com.ys.event_store.domain.CreateEventCommand;
import com.ys.event_store.domain.Event;

public interface CreateEventUseCase {

    Event create(CreateEventCommand command);
}
