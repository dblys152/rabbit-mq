package com.ys.event_store.domain;

import com.ys.infrastructure.data.LongId;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EventId implements LongId {
    @NotNull
    Long id;

    public static EventId of(Long id) {
        return new EventId(id);
    }

    @Override
    public Long get() {
        return this.id;
    }
}
