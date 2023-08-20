package com.ys.event_store.adapter.in.model;

import com.ys.event_store.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventModel {

    String eventId;
    String type;
    Object payload;
    LocalDateTime occurredAt;

    public static EventModel fromDomain(Event event) {
        return new EventModel(
                String.valueOf(event.getEventId()),
                event.getType(),
                event.getPayload(),
                event.getOccurredAt()
        );
    }
}
