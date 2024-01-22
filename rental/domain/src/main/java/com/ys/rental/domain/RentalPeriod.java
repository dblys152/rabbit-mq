package com.ys.rental.domain;

import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class RentalPeriod {
    LocalDateTime startedAt;
    LocalDateTime endedAt;

    private RentalPeriod(LocalDateTime startedAt, LocalDateTime endedAt) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        validation();
    }

    private void validation() {
        if (this.startedAt.isAfter(this.endedAt)) {
            throw new IllegalArgumentException("대여일이 반납 예정일보다 이후 일 수 없습니다.");
        }
    }
}
