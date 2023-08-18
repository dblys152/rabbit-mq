package com.ys.infrastructure.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.uuid.Generators;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
public class DomainEvent<T> implements Serializable {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private final String id;
    private final String type;
    private final T payload;
    private final LocalDateTime occurredAt;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DomainEvent(
            @JsonProperty("id") String id,
            @JsonProperty("type") String type,
            @JsonProperty("payload") T payload,
            @JsonProperty("occurredAt") LocalDateTime occurredAt
    ) {
        this.id = id;
        this.type = type;
        this.payload = payload;
        this.occurredAt = occurredAt;
    }

    public static <T> DomainEvent of(String type, T payload) {
        String id = Generators.timeBasedEpochGenerator().generate().toString();
        LocalDateTime occurredAt = LocalDateTime.now();
        return new DomainEvent(id, type, payload, occurredAt);
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
                    this.occurredAt
            );
        } catch (JsonProcessingException e) {
            throw new DomainEventException(this.type, e);
        }
    }

    public static DomainEvent deserialize(String serializedDomainEvent, Class<?> payloadType) {
        try {
            DomainEvent<String> domainEvent = objectMapper.readValue(serializedDomainEvent, DomainEvent.class);
            return payloadType == String.class ? domainEvent : deserializePayload(domainEvent, payloadType);
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
                    serializedEvent.getOccurredAt()
            );
        } catch (JsonProcessingException e) {
            throw new DomainEventException(serializedEvent.getType(), e);
        }
    }
}
