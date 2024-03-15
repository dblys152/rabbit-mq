package com.ys.product.adapter.in;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeadLetterProductConsumer {
    @RabbitListener(queues = "${rabbitmq.queue.dead-letter-product}")
    public void onDeadLetterMessage(Message message) {
        log.error("Fail product consumer. ID: {}, Message: {}", message.getMessageProperties().getMessageId(), new String(message.getBody()));
    }
}
