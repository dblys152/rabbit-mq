package com.ys.product.adapter.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_EXCHANGE_PRODUCT_ROUTING_KEY)
                .build();
    }
    @Bean
    public Queue deadLetterProductQueue() {
        return QueueBuilder.durable(DEAD_LETTER_PRODUCT_QUEUE).build();
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
    public SimpleRabbitListenerContainerFactory containerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAdviceChain(
                RetryInterceptorBuilder.stateless()
                        .maxAttempts(3)
                        .backOffOptions(1000, 2, 2000)
                        .recoverer(new RejectAndDontRequeueRecoverer())
                        .build());
        factory.setChannelTransacted(true);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }
}
