package com.ys.event_store.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class CreateEventRequest {

    @NotNull
    @Size(min = 1, max = 100)
    private String type;

    @NotNull
    private Map<String, Object> payload;

    @NotNull
    private LocalDateTime occurredAt;
}
