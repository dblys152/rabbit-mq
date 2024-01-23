package com.ys.rental.application.message;

import org.springframework.messaging.Message;

public interface MessageSender<T> {

    void send(Message<T> message);
}