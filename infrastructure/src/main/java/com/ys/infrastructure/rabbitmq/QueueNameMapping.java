package com.ys.infrastructure.rabbitmq;

public interface QueueNameMapping<T> {
    T get(String key);
}
