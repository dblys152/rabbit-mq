package com.ys.rental.application.event;

import com.ys.infrastructure.message.DomainEvent;
import com.ys.infrastructure.message.DomainEventPublisher;
import com.ys.rental.domain.Rental;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RentalEventPublisher implements DomainEventPublisher<Rental> {
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(String eventType, Rental payload, LocalDateTime publishedAt) {
        //PayloadInfo payloadInfo = PayloadInfoStore.THREAD_LOCAL.get();
        String publisherId = "SYSTEM";
        eventPublisher.publishEvent(DomainEvent.create(
                eventType, payload, publisherId, publishedAt));
    }
}
