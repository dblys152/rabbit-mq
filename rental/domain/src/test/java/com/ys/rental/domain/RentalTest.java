package com.ys.rental.domain;

import com.ys.rental.domain.fixture.SupportRentalFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class RentalTest extends SupportRentalFixture {
    private Rental rental;
    private Rental returnedRental;
    private Rental canceledRental;

    @BeforeEach
    void setUp() {
        rental = Rental.of(
                RENTAL_ID, USER_ID, RentalStatus.RENTED, RENTAL_LINES, RENTAL_PERIOD, null, NOW, NOW, 0L);
        returnedRental = Rental.of(
                RENTAL_ID, USER_ID, RentalStatus.RETURNED, RENTAL_LINES, RENTAL_PERIOD, NOW.plusHours(2), NOW, NOW, 0L);
        canceledRental = Rental.of(
                RENTAL_ID, USER_ID, RentalStatus.CANCELED, RENTAL_LINES, RENTAL_PERIOD, null, NOW, NOW, 0L);
    }

    @Test
    void 상품을_대여한다() {
        DoRentalCommand command = new DoRentalCommand(USER_ID, RENTAL_LINES, RENTAL_PERIOD);

        Rental actual = Rental.create(RENTAL_ID, command);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getUserId()).isEqualTo(command.getUserId()),
                () -> assertThat(actual.getStatus()).isEqualTo(RentalStatus.RENTED),
                () -> assertThat(actual.getRentalLines().isEmpty()).isFalse(),
                () -> assertThat(actual.getRentalPeriod()).isEqualTo(command.getRentalPeriod())
        );
    }

    @Test
    void 대여_상품을_반납한다() {
        LocalDateTime returnedAt = NOW.plusHours(2);
        DoReturnCommand command = new DoReturnCommand(returnedAt);

        rental.doReturn(command);

        assertAll(
                () -> assertThat(rental.getStatus()).isEqualTo(RentalStatus.RETURNED),
                () -> assertThat(rental.getReturnedAt()).isNotNull(),
                () -> assertThat(rental.getReturnedAt()).isAfter(rental.getRentalPeriod().getStartedAt())
        );
    }

    @Test
    void 반납_시_대여중인_상태가_아니면_에러를_반환한다() {
        assertThatThrownBy(() -> canceledRental.doCancel()).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> returnedRental.doCancel()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 반납_시_반납일이_대여일보다_이전이면_에러를_반환한다() {
        LocalDateTime returnedAt = rental.getRentalPeriod().getStartedAt().minusHours(1);
        DoReturnCommand command = new DoReturnCommand(returnedAt);

        assertThatThrownBy(() -> rental.doReturn(command)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 대여를_취소한다() {
        rental.doCancel();

        assertThat(rental.getStatus()).isEqualTo(RentalStatus.CANCELED);
    }

    @Test
    void 취소_시_대여중인_상태가_아니면_에러를_반환한다() {
        assertThatThrownBy(() -> returnedRental.doCancel()).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> canceledRental.doCancel()).isInstanceOf(IllegalStateException.class);
    }
}