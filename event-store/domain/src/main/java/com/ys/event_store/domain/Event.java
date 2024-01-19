package com.ys.event_store.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Event {
    @NotNull
    private EventId eventId;

    @NotNull
    @Size(min = 1, max = 100)
    private String type;

    @NotNull
    private Map<String, Object> payload;

    @Size(min = 1, max = 39)
    private String publisherId;

    @NotNull
    private LocalDateTime publishedAt;

    @NotNull
    private LocalDateTime occurredAt;

    public static Event of(
            EventId eventId,
            String type,
            Map<String, Object> payload,
            String publisherId,
            LocalDateTime publishedAt,
            LocalDateTime occurredAt
    ) {
        return new Event(eventId, type, payload, publisherId, publishedAt, occurredAt);
    }

    public static Event create(EventId eventId, CreateEventCommand command) {
        return new Event(
                eventId,
                command.getType(),
                command.getPayload(),
                command.getPublisherId(),
                command.getPublishedAt(),
                LocalDateTime.now()
        );
    }
}
