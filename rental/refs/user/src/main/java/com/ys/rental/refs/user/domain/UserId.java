package com.ys.rental.refs.user.domain;

import com.ys.shared.data.LongId;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class UserId implements LongId {
    @NotNull
    Long id;

    @Override
    public Long get() {
        return this.id;
    }
}
