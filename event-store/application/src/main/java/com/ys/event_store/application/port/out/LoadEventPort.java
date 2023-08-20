package com.ys.event_store.application.port.out;

import com.ys.event_store.domain.Events;

import java.time.LocalDateTime;

public interface LoadEventPort {

    Events selectAllByTypeAndOccurredAtBetween(String type, LocalDateTime startAt, LocalDateTime endAt);
}
