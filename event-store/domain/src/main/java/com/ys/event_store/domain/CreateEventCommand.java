package com.ys.event_store.domain;

import com.ys.shared.utils.SelfValidating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateEventCommand extends SelfValidating<CreateEventCommand> {
    @NotNull
    @Size(min = 1, max = 100)
    String type;

    @NotNull
    private Object payload;

    @Size(min = 1, max = 39)
    private String publisherId;

    @NotNull
    private LocalDateTime publishedAt;

    public CreateEventCommand(String type, Object payload, String publisherId, LocalDateTime publishedAt) {
        this.type = type;
        this.payload = payload;
        this.publisherId = publisherId;
        this.publishedAt = publishedAt;
        validateSelf();
    }
}
