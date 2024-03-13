package com.ys.rental.adapter.config;

import com.ys.shared.queue.RabbitMqExchange;
import com.ys.shared.queue.RabbitMqExchangeNameMapping;
import com.ys.rental.domain.event.RentalEventType;
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
    @Value("${rabbitmq.queue.register-event-store}")
    private String REGISTER_EVENT_STORE_QUEUE;
    @Value("${rabbitmq.queue.change-product-status}")
    private String CHANGE_PRODUCT_STATUS_QUEUE;

    @Value("${rabbitmq.exchange.rental}")
    private String RENTAL_EXCHANGE;

    /* Queue */
    @Bean
    public Queue registerEventStoreQueue() {
        return new Queue(REGISTER_EVENT_STORE_QUEUE, true);
    }
    @Bean
    public Queue changeProductStatusQueue() {
        return new Queue(CHANGE_PRODUCT_STATUS_QUEUE, true);
    }

    /* Exchange */
    @Bean
    public FanoutExchange rentalExchange() {
        return new FanoutExchange(RENTAL_EXCHANGE);
    }

    /* Binding */
    @Bean
    public Binding registerEventStoreQueueToRentalExchangeBinding() {
        return BindingBuilder.bind(registerEventStoreQueue()).to(rentalExchange());
    }
    @Bean
    public Binding changeProductStatusQueueToRentalExchangeBinding() {
        return BindingBuilder.bind(changeProductStatusQueue()).to(rentalExchange());
    }

    /* Server Connection */
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareQueue(registerEventStoreQueue());
        rabbitAdmin.declareQueue(changeProductStatusQueue());
        rabbitAdmin.declareExchange(rentalExchange());
        rabbitAdmin.declareBinding(registerEventStoreQueueToRentalExchangeBinding());
        rabbitAdmin.declareBinding(changeProductStatusQueueToRentalExchangeBinding());
        return rabbitAdmin;
    }

    // Queue Name Mapping For Message Sender
    @Bean
    public RabbitMqExchangeNameMapping rabbitMqExchangeNameMapping() {
        RabbitMqExchangeNameMapping mapping = new RabbitMqExchangeNameMapping();
        mapping.add(RentalEventType.DO_RENTAL_EVENT.name(), RabbitMqExchange.of(RENTAL_EXCHANGE, null));
        mapping.add(RentalEventType.DO_CANCEL_RENTAL_EVENT.name(), RabbitMqExchange.of(RENTAL_EXCHANGE, null));
        mapping.add(RentalEventType.DO_RETURN_RENTAL_EVENT.name(), RabbitMqExchange.of(RENTAL_EXCHANGE, null));
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
