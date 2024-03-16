package com.ys.product.adapter.in;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class DeadLetterProductConsumer {
    @RabbitListener(queues = "${rabbitmq.queue.dead-letter-product}")
    public void onDeadLetterMessage(Message message, Channel channel) throws IOException {
        log.error("Fail product consumer. Message: {}", new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
