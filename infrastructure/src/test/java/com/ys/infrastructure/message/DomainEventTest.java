package com.ys.infrastructure.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DomainEventTest {
    private static final String ANY_ID = "ANY_ID";
    private static final String ANY_EVENT_TYPE = "com.ys.test.AnyEvent";
    private static final String ANY_OCCURRED_AT_STRING = "2023-06-20T02:53:00.299046";

    private DomainEvent<AnyEvent> domainEvent;
    private String serializedEvent;

    @BeforeEach
    void setUp() {
        domainEvent = DomainEvent.of(ANY_EVENT_TYPE, new AnyEvent("ANY_VALUE"));
        serializedEvent = "{\"id\":\"" + ANY_ID + "\","
                + "\"type\":\"" + ANY_EVENT_TYPE + "\","
                + "\"payload\":\"{\\\"value\\\":\\\"ANY_VALUE\\\"}\","
                + "\"occurredAt\":\"" + ANY_OCCURRED_AT_STRING + "\"}";
    }

    @Test
    void 도메인_이벤트_직렬화() {
        String actual = domainEvent.serialize();

        assertThat(actual).isNotNull();
    }

    @Test
    void 도메인_이벤트_페이로드_직렬화() {
        DomainEvent actual = domainEvent.serializePayload();

        assertThat(actual).isNotNull();
        assertThat(actual.getPayload()).isEqualTo("{\"value\":\"ANY_VALUE\"}");
    }

    @Test
    void 도메인_이벤트_역직렬화() {
        DomainEvent<AnyEvent> actual = DomainEvent.deserialize(serializedEvent, AnyEvent.class);
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(ANY_EVENT_TYPE),
                () -> assertThat(actual.getPayload()).isInstanceOf(AnyEvent.class),
                () -> assertThat(actual.getOccurredAt()).isEqualTo(ANY_OCCURRED_AT_STRING)
        );
    }

    @Test
    void 도메인_이벤트_역직렬화_payload_string() {
        DomainEvent<String> actual = DomainEvent.deserialize(serializedEvent, String.class);
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(ANY_EVENT_TYPE),
                () -> assertThat(actual.getPayload()).isInstanceOf(String.class),
                () -> assertThat(actual.getOccurredAt()).isEqualTo(ANY_OCCURRED_AT_STRING)
        );
    }

    @Test
    void 도메인_이벤트_페이로드_역직렬화() {
        DomainEvent eventOfSerializedPayload = domainEvent.serializePayload();

        DomainEvent<AnyEvent> actual = DomainEvent.deserializePayload(eventOfSerializedPayload, AnyEvent.class);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(ANY_EVENT_TYPE),
                () -> assertThat(actual.getPayload()).isInstanceOf(AnyEvent.class),
                () -> assertThat(actual.getOccurredAt()).isNotNull()
        );
    }

    private static class AnyEvent {
        private String value;

        @JsonCreator
        public AnyEvent(@JsonProperty("value") String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}