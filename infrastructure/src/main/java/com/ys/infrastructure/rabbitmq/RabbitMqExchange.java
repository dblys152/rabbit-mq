package com.ys.infrastructure.rabbitmq;

import lombok.Value;

@Value(staticConstructor = "of")
public class RabbitMqExchange {
    String name;
    String routingKey;
}
