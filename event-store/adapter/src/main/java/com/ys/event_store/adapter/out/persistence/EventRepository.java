package com.ys.event_store.adapter.out.persistence;


import com.ys.event_store.domain.Event;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EventRepository {

    void insert(Event event);

    List<Event> selectAllByTypeAndPublishedAtBetween(Map<String, Object> parameterType);
}
