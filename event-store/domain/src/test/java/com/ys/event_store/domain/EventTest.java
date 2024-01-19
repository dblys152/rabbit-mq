package com.ys.event_store.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {
    private static final EventId EVENT_ID = EventId.of(999999999L);
    private static final String ANY_TYPE = "PRODUCT_RENTED_EVENT";
    private static final String ANY_PUBLISHER_ID = "SYSTEM";
    private static final LocalDateTime NOW = LocalDateTime.now();

    @Test
    void 이벤트를_생성한다() {
        CreateEventCommand command = new CreateEventCommand(ANY_TYPE, getAnyPayload(), ANY_PUBLISHER_ID, NOW);

        Event actual = Event.create(EVENT_ID, command);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(command.getType()),
                () -> assertThat(actual.getPayload()).isEqualTo(command.getPayload()),
                () -> assertThat(actual.getPublisherId()).isEqualTo(command.getPublisherId()),
                () -> assertThat(actual.getPublishedAt()).isEqualTo(command.getPublishedAt()),
                () -> assertThat(actual.getOccurredAt()).isNotNull()
        );
    }

    private Map<String, Object> getAnyPayload() {
        Map<String, Object> anyPayload = new HashMap<>();
        anyPayload.put("id", 123);
        anyPayload.put("name", "ANY_NAME");

        Map<String, Object> anyData = new HashMap<>();
        anyData.put("id", 333);
        anyData.put("name", "ANY_NAME");
        anyData.put("createdAt", LocalDateTime.now());

        anyPayload.put("data", anyData);

        return anyPayload;
    }
}