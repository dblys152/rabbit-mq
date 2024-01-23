package com.ys.infrastructure.rabbitmq;

import java.util.HashMap;
import java.util.Map;

public class RabbitMqExchangeNameMapping implements QueueNameMapping<RabbitMqExchange> {
    private final Map<String, RabbitMqExchange> topicMap = new HashMap<>();

    public void add(String key, RabbitMqExchange rabbitMqTopic) {
        topicMap.put(key, rabbitMqTopic);
    }

    @Override
    public RabbitMqExchange get(String key) {
        return topicMap.get(key);
    }
}
