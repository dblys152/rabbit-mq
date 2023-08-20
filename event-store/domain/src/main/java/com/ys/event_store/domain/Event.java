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
    private Long eventId;

    @NotNull
    @Size(min = 1, max = 100)
    private String type;

    @NotNull
    private Map<String, Object> payload;

    @NotNull
    private LocalDateTime occurredAt;

    @NotNull
    private LocalDateTime createdAt;

    public static Event of(
            Long eventId,
            String type,
            Map<String, Object> payload,
            LocalDateTime occurredAt,
            LocalDateTime createdAt
    ) {
        return new Event(eventId, type, payload, occurredAt, createdAt);
    }

    public static Event create(CreateEventCommand command) {
        return new Event(
                null,
                command.getType(),
                command.getPayload(),
                command.getOccurredAt(),
                LocalDateTime.now()
        );
    }
}
