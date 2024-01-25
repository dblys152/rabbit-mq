package com.ys.rental.adapter.config;

import com.ys.infrastructure.rabbitmq.RabbitMqExchange;
import com.ys.infrastructure.rabbitmq.RabbitMqExchangeNameMapping;
import com.ys.rental.domain.RentalEventType;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
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

    // Name Mapping For Message Sender
    @Bean
    public RabbitMqExchangeNameMapping rabbitMqExchangeNameMapping() {
        RabbitMqExchangeNameMapping mapping = new RabbitMqExchangeNameMapping();
        mapping.add(RentalEventType.DO_RENTAL_EVENT.name(), RabbitMqExchange.of(RENTAL_EXCHANGE, null));
        mapping.add(RentalEventType.DO_CANCEL_RENTAL_EVENT.name(), RabbitMqExchange.of(RENTAL_EXCHANGE, null));
        mapping.add(RentalEventType.DO_RETURN_RENTAL_EVENT.name(), RabbitMqExchange.of(RENTAL_EXCHANGE, null));
        return mapping;
    }
}
