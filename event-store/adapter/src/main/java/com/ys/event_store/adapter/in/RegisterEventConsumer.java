package com.ys.event_store.adapter.in;

import com.rabbitmq.client.Channel;
import com.ys.event_store.application.port.in.RegisterEventUseCase;
import com.ys.event_store.domain.CreateEventCommand;
import com.ys.shared.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterEventConsumer {
    private final RegisterEventUseCase registerEventUseCase;

    @RabbitListener(queues = "${rabbitmq.queue.register-event-store}")
    public void receive(Message message, Channel channel) throws IOException {
        String messageBody = new String(message.getBody());
        log.info("Received a message. Message: {}", messageBody);

        try {
            DomainEvent<Object> domainEvent = DomainEvent.deserialize(messageBody, Object.class);
            registerEventUseCase.register(new CreateEventCommand(
                    domainEvent.getType(), domainEvent.getPayload(), domainEvent.getPublisherId(), domainEvent.getPublishedAt()));

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception ex) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
