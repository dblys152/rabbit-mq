package com.ys.product.adapter.in;

import com.ys.infrastructure.event.EventMessageEnvelopProcessTemplate;
import com.ys.product.refs.rental.domain.RentalEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class ChangeProductStatusConsumer {
    @Setter(value = AccessLevel.PACKAGE)
    private EventMessageEnvelopProcessTemplate template = new EventMessageEnvelopProcessTemplate();

    private final Consumer<RentalEvent> processor;

    @RabbitListener(queues = "${rabbitmq.queue.change-product-status}")
    public void receive(String message) {
        template.doProcess(message, RentalEvent.class, processor);
    }
}
