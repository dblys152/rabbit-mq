package com.ys.infrastructure.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.uuid.Generators;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class DomainEvent<T> implements Serializable {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @NotBlank
    private final String id;

    @NotBlank
    private final String type;

    @NotNull
    private final T payload;

    @Size(min = 1, max = 39)
    private final String publisherId;

    @NotNull
    private final LocalDateTime publishedAt;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DomainEvent(
            @JsonProperty("id") String id,
            @JsonProperty("type") String type,
            @JsonProperty("payload") T payload,
            @JsonProperty("publisherId") String publisherId,
            @JsonProperty("publishedAt") LocalDateTime publishedAt
    ) {
        this.id = id;
        this.type = type;
        this.payload = payload;
        this.publisherId = publisherId;
        this.publishedAt = publishedAt;
    }

    public static <T> DomainEvent<T> create(String type, T payload, String publisherId, LocalDateTime publishedAt) {
        String id = Generators.timeBasedEpochGenerator().generate().toString();
        LocalDateTime occurredAt = LocalDateTime.now();
        return new DomainEvent<>(id, type, payload, publisherId, publishedAt);
    }

    public String serialize() {
        try {
            DomainEvent<String> domainEvent = this.serializePayload();
            return objectMapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
            throw new DomainEventException(this.type, e);
        }
    }

    public DomainEvent<String> serializePayload() {
        try {
            return new DomainEvent<>(
                    this.id,
                    this.type,
                    objectMapper.writeValueAsString(this.payload),
                    this.publisherId,
                    this.publishedAt
            );
        } catch (JsonProcessingException e) {
            throw new DomainEventException(this.type, e);
        }
    }

    public static <T> DomainEvent<T> deserialize(String serializedDomainEvent, Class<T> payloadType) {
        try {
            DomainEvent<String> domainEvent = objectMapper.readValue(serializedDomainEvent, DomainEvent.class);
            return payloadType == String.class ? (DomainEvent<T>) domainEvent : deserializePayload(domainEvent, payloadType);
        } catch (JsonProcessingException e) {
            throw new DomainEventException(serializedDomainEvent, e);
        }
    }

    public static <T> DomainEvent<T> deserializePayload(DomainEvent<String> serializedEvent, Class<T> payloadType) {
        try {
            T deserializedPayload = objectMapper.readValue(serializedEvent.getPayload(), payloadType);
            return new DomainEvent<>(
                    serializedEvent.getId(),
                    serializedEvent.getType(),
                    deserializedPayload,
                    serializedEvent.getPublisherId(),
                    serializedEvent.getPublishedAt()
            );
        } catch (JsonProcessingException e) {
            throw new DomainEventException(serializedEvent.getType(), e);
        }
    }
}
