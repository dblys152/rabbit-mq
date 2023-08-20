package com.ys.event_store.adapter.in;

import com.ys.event_store.application.port.in.GetEventQuery;
import com.ys.event_store.adapter.in.model.EventModel;
import com.ys.event_store.domain.Events;
import com.ys.infrastructure.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/events",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EventQueryController {

    private final GetEventQuery getEventQuery;

    @GetMapping("")
    public ResponseEntity getAll(
            @RequestParam("type") String type,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {

        LocalDateTime startAt = startDate.atStartOfDay();
        LocalDateTime endAt = endDate.atTime(23, 59, 59, 999);
        Events events = getEventQuery.getAllByTypeAndOccurredAtBetween(type, startAt, endAt);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(HttpStatus.OK.value(), events.getItems().stream()
                        .map(EventModel::fromDomain)
                        .toList()));
    }
}
