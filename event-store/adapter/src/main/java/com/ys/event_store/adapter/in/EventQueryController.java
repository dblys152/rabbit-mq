package com.ys.event_store.adapter.in;

import com.ys.event_store.adapter.in.model.EventModel;
import com.ys.event_store.application.port.in.GetEventQuery;
import com.ys.event_store.domain.Events;
import com.ys.infrastructure.utils.ApiResponseModel;
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
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/events",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EventQueryController {
    private final GetEventQuery getEventQuery;

    @GetMapping("")
    public ResponseEntity<ApiResponseModel<List<EventModel>>> getAll(
            @RequestParam("type") String type,
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {

        LocalDateTime startAt = startDate.atStartOfDay();
        LocalDateTime endAt = endDate.atTime(LocalTime.MAX);
        Events events = getEventQuery.getAllByTypeAndPublishedAtBetween(type, startAt, endAt);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponseModel.success(
                HttpStatus.OK.value(),
                events.getItems().stream()
                        .map(EventModel::fromDomain)
                        .toList()));
    }
}
