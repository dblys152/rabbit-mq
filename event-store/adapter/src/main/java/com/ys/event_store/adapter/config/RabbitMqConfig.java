package com.ys.event_store.adapter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMqConfig {
    private static final String QUORUM_QUEUE_TYPE = "quorum";

    @Value("${rabbitmq.queue.register-event-store}")
    private String REGISTER_EVENT_STORE_QUEUE;
    @Value("${rabbitmq.queue.dead-letter-event-store}")
    private String DEAD_LETTER_EVENT_STORE_QUEUE;

    @Value("${rabbitmq.exchange.dead-letter.name}")
    private String DEAD_LETTER_EXCHANGE;
    @Value("${rabbitmq.exchange.dead-letter.event-store-routing-key}")
    private String DEAD_LETTER_EXCHANGE_EVENT_STORE_ROUTING_KEY;

    /* Queue */
    @Bean
    public Queue registerEventStoreQueue() {
        return makeQueue(REGISTER_EVENT_STORE_QUEUE, QUORUM_QUEUE_TYPE, 4, DEAD_LETTER_EXCHANGE, DEAD_LETTER_EXCHANGE_EVENT_STORE_ROUTING_KEY);
    }
    @Bean
    public Queue deadLetterEventStoreQueue() {
        return makeQueue(DEAD_LETTER_EVENT_STORE_QUEUE, QUORUM_QUEUE_TYPE, 4);
    }
    private Queue makeQueue(String name, String type, int deliveryLimit, String dlxName, String dlxRoutingKey) {
        return QueueBuilder.durable(name)
                .withArgument("x-queue-type", type)
                .withArgument("x-delivery-limit", deliveryLimit)
                .withArgument("x-dead-letter-exchange", dlxName)
                .withArgument("x-dead-letter-routing-key", dlxRoutingKey)
                .build();
    }
    private Queue makeQueue(String name, String type, int deliveryLimit) {
        return QueueBuilder.durable(name)
                .withArgument("x-queue-type", type)
                .withArgument("x-delivery-limit", deliveryLimit)
                .build();
    }

    /* Exchange */
    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DEAD_LETTER_EXCHANGE);
    }

    /* Server Connection */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareQueue(registerEventStoreQueue());
        rabbitAdmin.declareQueue(deadLetterEventStoreQueue());
        rabbitAdmin.declareExchange(deadLetterExchange());
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                Message message = correlationData.getReturned().getMessage();
                byte[] body = message.getBody();
                log.error("Fail to produce. ID: {}, Message: {}", correlationData.getId(), body);
            }
        });

        return rabbitTemplate;
    }
}
