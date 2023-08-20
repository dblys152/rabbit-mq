package com.ys.event_store.domain;

import com.ys.infrastructure.utils.SelfValidating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Value(staticConstructor = "of")
public class CreateEventCommand extends SelfValidating<CreateEventCommand> {

    @NotNull
    @Size(min = 1, max = 100)
    String type;

    @NotNull
    Map<String, Object> payload;

    @NotNull
    LocalDateTime occurredAt;

    public CreateEventCommand(String type, Map<String, Object> payload, LocalDateTime occurredAt) {
        this.type = type;
        this.payload = payload;
        this.occurredAt = occurredAt;
        validateSelf();
    }
}
