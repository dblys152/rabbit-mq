package com.ys.infrastructure.message;

import lombok.Getter;

public class DomainEventException extends RuntimeException {

    @Getter
    private final String eventType;

    public DomainEventException(String eventType, Exception e) {
        super(e);
        this.eventType = eventType;
    }
}
