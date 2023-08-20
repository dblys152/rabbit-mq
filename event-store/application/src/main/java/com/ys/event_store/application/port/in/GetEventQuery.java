package com.ys.event_store.application.port.in;

import com.ys.event_store.domain.Events;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface GetEventQuery {

    Events getAllByTypeAndOccurredAtBetween(String type, LocalDateTime startAt, LocalDateTime endAt);
}
