package com.ys.event_store.application.service;

import com.ys.event_store.application.port.in.GetEventQuery;
import com.ys.event_store.application.port.out.LoadEventPort;
import com.ys.event_store.domain.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class EventQueryService implements GetEventQuery {

    private final LoadEventPort loadEventPort;

    @Override
    public Events getAllByTypeAndPublishedAtBetween(String type, LocalDateTime startAt, LocalDateTime endAt) {
        return loadEventPort.selectAllByTypeAndPublishedAtBetween(type, startAt, endAt);
    }
}
