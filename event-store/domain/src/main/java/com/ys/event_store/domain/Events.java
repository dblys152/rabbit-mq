package com.ys.event_store.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class Events {
    @Valid
    @NotNull
    List<Event> items;
}
