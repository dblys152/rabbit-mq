package com.ys.event_store.adapter.out.persistence;

import com.ys.event_store.adapter.config.MybatisConfig;
import com.ys.event_store.adapter.config.ObjectMapperConfig;
import com.ys.event_store.domain.CreateEventCommand;
import com.ys.event_store.domain.Event;
import com.ys.event_store.domain.EventId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {MybatisConfig.class, ObjectMapperConfig.class})
class EventRepositoryTest {
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final EventId EVENT_ID = EventId.of(999999999L);
    private static final String ANY_TYPE = "PRODUCT_CREATED_EVENT";
    private static final String ANY_PUBLISHER_ID = "1";
    private static final LocalDate NOW_DATE = NOW.toLocalDate();
    private static final LocalDateTime START_AT = NOW_DATE.atStartOfDay();
    private static final LocalDateTime END_AT = NOW_DATE.atTime(23, 59, 59, 999);

    @Autowired
    private EventRepository repository;

    private Event event;

    @BeforeEach
    void setUp() {
        CreateEventCommand command = new CreateEventCommand(ANY_TYPE, getAnyPayload(), ANY_PUBLISHER_ID, NOW);
        event = Event.create(EVENT_ID, command);
    }

    @Test
    void insert() {
        repository.insert(event);
    }

    @Test
    void selectAllByTypeAndOccurredAtBetween() {
        repository.insert(event);

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("type", ANY_TYPE);
        parameterMap.put("startAt", START_AT);
        parameterMap.put("endAt", END_AT);

        List<Event> actual = repository.selectAllByTypeAndPublishedAtBetween(parameterMap);

        assertThat(actual).isNotEmpty();
    }

    private Map<String, Object> getAnyPayload() {
        Map<String, Object> anyPayload = new HashMap<>();
        anyPayload.put("id", 123);
        anyPayload.put("name", "ANY_NAME");

        Map<String, Object> anyData = new HashMap<>();
        anyData.put("id", 333);
        anyData.put("name", "ANY_NAME");
        anyData.put("createdAt", LocalDateTime.now());

        anyPayload.put("data", anyData);

        return anyPayload;
    }
}