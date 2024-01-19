package com.ys.event_store.adapter.out;

import com.ys.event_store.adapter.out.persistence.EventRepository;
import com.ys.event_store.application.port.out.LoadEventPort;
import com.ys.event_store.application.port.out.RecordEventPort;
import com.ys.event_store.domain.Event;
import com.ys.event_store.domain.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EventPersistenceAdapter implements RecordEventPort, LoadEventPort {

    private final EventRepository repository;

    @Override
    public void insert(Event event) {
        repository.insert(event);
    }

    @Override
    public Events selectAllByTypeAndPublishedAtBetween(String type, LocalDateTime startAt, LocalDateTime endAt) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("type", type);
        parameterMap.put("startAt", startAt);
        parameterMap.put("endAt", endAt);

        List<Event> entityList = repository.selectAllByTypeAndPublishedAtBetween(parameterMap);

        return Events.of(entityList);
    }
}
