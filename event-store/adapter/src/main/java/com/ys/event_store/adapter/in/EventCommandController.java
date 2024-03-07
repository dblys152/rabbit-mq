package com.ys.event_store.adapter.in;

import com.ys.event_store.adapter.in.model.EventModel;
import com.ys.event_store.application.port.in.RegisterEventUseCase;
import com.ys.event_store.domain.CreateEventCommand;
import com.ys.event_store.domain.Event;
import com.ys.shared.event.DomainEvent;
import com.ys.shared.utils.ApiResponseModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/events",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EventCommandController {

    private final RegisterEventUseCase registerEventUseCase;

    @PostMapping("")
    public ResponseEntity<ApiResponseModel<EventModel>> register(@Valid @RequestBody DomainEvent<Map<String, Object>> domainEvent) {
        Event event = registerEventUseCase.register(new CreateEventCommand(
                domainEvent.getType(), domainEvent.getPayload(), domainEvent.getPublisherId(), domainEvent.getPublishedAt()));

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseModel.success(
                HttpStatus.CREATED.value(), EventModel.fromDomain(event)));
    }
}
