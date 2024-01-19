package com.ys.infrastructure.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DomainEventTest {
    private static final String ANY_ID = "ANY_ID";
    private static final String ANY_EVENT_TYPE = "com.ys.test.AnyEvent";
    private static final String ANY_PUBLISHER_ID = "U001";
    private static final LocalDateTime ANY_PUBLISHED_AT = LocalDateTime.of(2023, 11, 3, 13, 53, 7);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private DomainEvent<AnyEvent> domainEvent;
    private String serializedEvent;

    @BeforeEach
    void setUp() {
        domainEvent = new DomainEvent<>(ANY_ID, ANY_EVENT_TYPE, new AnyEvent("ANY_VALUE"), ANY_PUBLISHER_ID, ANY_PUBLISHED_AT);
        serializedEvent = "{\"id\":\"" + ANY_ID + "\","
                + "\"type\":\"" + ANY_EVENT_TYPE + "\","
                + "\"payload\":\"{\\\"value\\\":\\\"ANY_VALUE\\\"}\","
                + "\"publisherId\":\"" + ANY_PUBLISHER_ID + "\","
                + "\"publishedAt\":\"" + ANY_PUBLISHED_AT.format(formatter) + "\"}";
    }

    @Test
    void 도메인_이벤트_생성() {
        DomainEvent<AnyEvent> actual = DomainEvent.create(ANY_EVENT_TYPE, new AnyEvent("ANY_VALUE"), ANY_PUBLISHER_ID, ANY_PUBLISHED_AT);

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void 도메인_이벤트_직렬화() {
        String actual = domainEvent.serialize();

        assertThat(actual).isNotNull();
    }

    @Test
    void 도메인_이벤트_페이로드_직렬화() {
        DomainEvent<String> actual = domainEvent.serializePayload();

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
                () -> assertThat(actual.getPublisherId()).isEqualTo(ANY_PUBLISHER_ID),
                () -> assertThat(actual.getPublishedAt()).isEqualTo(ANY_PUBLISHED_AT)
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
                () -> assertThat(actual.getPublisherId()).isEqualTo(ANY_PUBLISHER_ID),
                () -> assertThat(actual.getPublishedAt()).isEqualTo(ANY_PUBLISHED_AT)
        );
    }

    @Test
    void 도메인_이벤트_페이로드_역직렬화() {
        DomainEvent<String> eventOfSerializedPayload = domainEvent.serializePayload();

        DomainEvent<AnyEvent> actual = DomainEvent.deserializePayload(eventOfSerializedPayload, AnyEvent.class);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getType()).isEqualTo(ANY_EVENT_TYPE),
                () -> assertThat(actual.getPayload()).isInstanceOf(AnyEvent.class),
                () -> assertThat(actual.getPublisherId()).isEqualTo(ANY_PUBLISHER_ID),
                () -> assertThat(actual.getPublishedAt()).isEqualTo(ANY_PUBLISHED_AT)
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