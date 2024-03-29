package com.ys.rental.adapter.config;

import com.ys.rental.domain.event.RentalEventType;
import com.ys.shared.queue.RabbitMqExchange;
import com.ys.shared.queue.RabbitMqExchangeNameMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
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
    @Value("${rabbitmq.queue.change-product-status}")
    private String CHANGE_PRODUCT_STATUS_QUEUE;
    @Value("${rabbitmq.queue.dead-letter-product}")
    private String DEAD_LETTER_PRODUCT_QUEUE;

    @Value("${rabbitmq.exchange.rental.name}")
    private String RENTAL_EXCHANGE;
    @Value("${rabbitmq.exchange.rental.default-routing-key}")
    private String RENTAL_EXCHANGE_DEFAULT_ROUTING_KEY;
    @Value("${rabbitmq.exchange.dead-letter.name}")
    private String DEAD_LETTER_EXCHANGE;
    @Value("${rabbitmq.exchange.dead-letter.event-store-routing-key}")
    private String DEAD_LETTER_EXCHANGE_EVENT_STORE_ROUTING_KEY;
    @Value("${rabbitmq.exchange.dead-letter.product-routing-key}")
    private String DEAD_LETTER_EXCHANGE_PRODUCT_ROUTING_KEY;

    /* Queue */
    @Bean
    public Queue registerEventStoreQueue() {
        return makeQueue(REGISTER_EVENT_STORE_QUEUE, QUORUM_QUEUE_TYPE, 4, DEAD_LETTER_EXCHANGE, DEAD_LETTER_EXCHANGE_EVENT_STORE_ROUTING_KEY);
    }
    @Bean
    public Queue deadLetterEventStoreQueue() {
        return makeQueue(DEAD_LETTER_EVENT_STORE_QUEUE, QUORUM_QUEUE_TYPE, 4);
    }
    @Bean
    public Queue changeProductStatusQueue() {
        return makeQueue(CHANGE_PRODUCT_STATUS_QUEUE, QUORUM_QUEUE_TYPE, 4, DEAD_LETTER_EXCHANGE, DEAD_LETTER_EXCHANGE_PRODUCT_ROUTING_KEY);
    }
    @Bean
    public Queue deadLetterProductQueue() {
        return makeQueue(DEAD_LETTER_PRODUCT_QUEUE, QUORUM_QUEUE_TYPE, 4);
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
    public TopicExchange rentalExchange() {
        return new TopicExchange(RENTAL_EXCHANGE);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DEAD_LETTER_EXCHANGE);
    }

    /** Binding
     * RentalExchange -> [RegisterEventStoreQueue, ChangeProductStatusQueue]
     * DeadLetterExchange -> [DeadLetterEventStoreQueue, DeadLetterProductQueue]
     */
    @Bean
    public Binding registerEventStoreQueueToRentalExchangeBinding() {
        return BindingBuilder.bind(registerEventStoreQueue())
                .to(rentalExchange())
                .with(RENTAL_EXCHANGE_DEFAULT_ROUTING_KEY);
    }
    @Bean
    public Binding changeProductStatusQueueToRentalExchangeBinding() {
        return BindingBuilder.bind(changeProductStatusQueue())
                .to(rentalExchange())
                .with(RENTAL_EXCHANGE_DEFAULT_ROUTING_KEY);
    }
    @Bean
    public Binding deadLetterEventStoreQueueToDeadLetterExchangeBinding() {
        return BindingBuilder.bind(deadLetterEventStoreQueue())
                .to(deadLetterExchange())
                .with(DEAD_LETTER_EXCHANGE_EVENT_STORE_ROUTING_KEY);
    }
    @Bean
    public Binding deadLetterProductQueueToDeadLetterExchangeBinding() {
        return BindingBuilder.bind(deadLetterProductQueue())
                .to(deadLetterExchange())
                .with(DEAD_LETTER_EXCHANGE_PRODUCT_ROUTING_KEY);
    }

    /* Server Connection */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareQueue(registerEventStoreQueue());
        rabbitAdmin.declareQueue(deadLetterEventStoreQueue());
        rabbitAdmin.declareQueue(changeProductStatusQueue());
        rabbitAdmin.declareQueue(deadLetterProductQueue());
        rabbitAdmin.declareExchange(rentalExchange());
        rabbitAdmin.declareExchange(deadLetterExchange());
        rabbitAdmin.declareBinding(registerEventStoreQueueToRentalExchangeBinding());
        rabbitAdmin.declareBinding(changeProductStatusQueueToRentalExchangeBinding());
        rabbitAdmin.declareBinding(deadLetterEventStoreQueueToDeadLetterExchangeBinding());
        rabbitAdmin.declareBinding(deadLetterProductQueueToDeadLetterExchangeBinding());
        return rabbitAdmin;
    }

    // Queue Name Mapping For Message Sender
    @Bean
    public RabbitMqExchangeNameMapping rabbitMqExchangeNameMapping() {
        RabbitMqExchangeNameMapping mapping = new RabbitMqExchangeNameMapping();
        RabbitMqExchange rentalExchange = RabbitMqExchange.of(RENTAL_EXCHANGE, RENTAL_EXCHANGE_DEFAULT_ROUTING_KEY);
        mapping.add(RentalEventType.DO_RENTAL_EVENT.name(), rentalExchange);
        mapping.add(RentalEventType.DO_CANCEL_RENTAL_EVENT.name(), rentalExchange);
        mapping.add(RentalEventType.DO_RETURN_RENTAL_EVENT.name(), rentalExchange);
        return mapping;
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
