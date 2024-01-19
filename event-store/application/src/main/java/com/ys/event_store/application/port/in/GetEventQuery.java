package com.ys.event_store.application.port.in;

import com.ys.event_store.domain.Events;

import java.time.LocalDateTime;

public interface GetEventQuery {

    Events getAllByTypeAndPublishedAtBetween(String type, LocalDateTime startAt, LocalDateTime endAt);
}
