package com.ys.rental.application.event;

import com.ys.infrastructure.event.DomainEvent;
import com.ys.infrastructure.event.DomainEventPublisher;
import com.ys.rental.domain.event.RentalEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RentalEventPublisher implements DomainEventPublisher<RentalEvent> {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(String eventType, RentalEvent payload, LocalDateTime publishedAt) {
        //PayloadInfo payloadInfo = PayloadInfoStore.THREAD_LOCAL.get();
        String publisherId = "SYSTEM";
        eventPublisher.publishEvent(DomainEvent.create(
                eventType, payload, publisherId, publishedAt));
    }
}
