package com.ys.product.adapter.config;

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
    @Value("${rabbitmq.queue.change-product-status}")
    private String CHANGE_PRODUCT_STATUS_QUEUE;
    @Value("${rabbitmq.queue.dead-letter-product}")
    private String DEAD_LETTER_PRODUCT_QUEUE;

    @Value("${rabbitmq.exchange.dead-letter.name}")
    private String DEAD_LETTER_EXCHANGE;
    @Value("${rabbitmq.exchange.dead-letter.product-routing-key}")
    private String DEAD_LETTER_EXCHANGE_PRODUCT_ROUTING_KEY;

    /* Queue */
    @Bean
    public Queue changeProductStatusQueue() {
        return QueueBuilder.durable(CHANGE_PRODUCT_STATUS_QUEUE)
                .withArgument("x-queue-type", "quorum")
                .withArgument("x-delivery-limit", 4)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_EXCHANGE_PRODUCT_ROUTING_KEY)
                .build();
    }
    @Bean
    public Queue deadLetterProductQueue() {
        return QueueBuilder.durable(DEAD_LETTER_PRODUCT_QUEUE)
                .withArgument("x-queue-type", "quorum")
                .withArgument("x-delivery-limit", 4)
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
        rabbitAdmin.declareQueue(changeProductStatusQueue());
        rabbitAdmin.declareQueue(deadLetterProductQueue());
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
