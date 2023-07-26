package com.ys.rental.refs.user.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class UserId {

    @NotNull
    String id;
}
