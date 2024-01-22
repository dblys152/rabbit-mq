package com.ys.rental.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class RentalPeriodTest {
    @Test
    public void 대여정보_생성_시_반납예정일이_대여일보다_이전이면_에러를_반환한다() {
        LocalDateTime startedAt = LocalDateTime.now();
        LocalDateTime endedAt = startedAt.minusHours(3);

        assertThatThrownBy(() -> RentalPeriod.of(startedAt, endedAt)).isInstanceOf(IllegalArgumentException.class);
    }
}