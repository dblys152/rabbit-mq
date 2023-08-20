package com.ys.event_store.application.port.out;

import com.ys.event_store.domain.Event;

public interface RecordEventPort {

    void insert(Event event);
}
