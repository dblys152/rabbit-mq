package com.ys.event_store.adapter.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.queue.register-event-store}")
    private String REGISTER_EVENT_STORE_QUEUE;

    /* Queue */
    @Bean
    public Queue registerEventStoreQueue() {
        return new Queue(REGISTER_EVENT_STORE_QUEUE, true);
    }

    /* Server Connection */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareQueue(registerEventStoreQueue());
        return rabbitAdmin;
    }
}
