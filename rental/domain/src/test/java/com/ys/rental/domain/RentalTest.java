package com.ys.rental.domain;

import com.ys.rental.domain.fixture.SupportRentalFixture;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class RentalTest extends SupportRentalFixture {

    @Test
    void 대여정보를_생성한다() {
        CreateRentalCommand command = CREATE_RENTAL_COMMAND;

        Rental actual = Rental.create(command);

        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getRentalId()).isNotNull(),
                () -> assertThat(actual.getUserId()).isEqualTo(command.getUserId()),
                () -> assertThat(actual.getStatus()).isEqualTo(RentalStatus.RENTED),
                () -> assertThat(actual.getRentalLines().isEmpty()).isFalse(),
                () -> assertThat(actual.getRentedAt()).isEqualTo(command.getRentedAt()),
                () -> assertThat(actual.getScheduledReturnAt()).isEqualTo(command.getScheduledReturnAt())
        );
    }

    @Test
    public void 대여정보_생성_시_반납예정일이_대여일보다_이전이면_에러를_반환한다() {
        LocalDateTime RentedAt = NOW.plusHours(3);
        LocalDateTime ScheduledReturnAt = NOW;
        CreateRentalCommand command = CreateRentalCommand.of(USER_ID, RENTAL_LINES, RentedAt, ScheduledReturnAt);

        assertThatThrownBy(() -> Rental.create(command)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 반납한다() {
        LocalDateTime rentedAt = NOW;
        LocalDateTime scheduledReturnAt = NOW.plusHours(3);
        Rental rental = Rental.of(
                RENTAL_ID, USER_ID, RentalStatus.RENTED, RENTAL_LINES, rentedAt, scheduledReturnAt, null, NOW, NOW, 0L);
        LocalDateTime returnedAt = NOW.plusHours(2);
        DoReturnCommand command = DoReturnCommand.of(returnedAt);

        rental.doReturn(command);

        assertAll(
                () -> assertThat(rental.getStatus()).isEqualTo(RentalStatus.RETURNED),
                () -> assertThat(rental.getReturnedAt()).isNotNull(),
                () -> assertThat(rental.getReturnedAt()).isAfter(rentedAt),
                () -> assertThat(rental.getReturnedAt().isAfter(scheduledReturnAt)
                        || rental.getReturnedAt().isBefore(scheduledReturnAt)).isTrue()
        );
    }

    @Test
    void 반납_시_대여중인_상태가_아니면_에러를_반환한다() {
        Rental canceledRental = CANCELED_RENTAL;
        Rental returnedRental = RETURNED_RENTAL;
        assertThatThrownBy(() -> canceledRental.doCancel()).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> returnedRental.doCancel()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 반납_시_반납일이_대여일보다_이전이면_에러를_반환한다() {
        LocalDateTime rentedAt = NOW.plusHours(1);
        LocalDateTime scheduledReturnAt = NOW.plusHours(3);
        Rental rental = Rental.of(
                RENTAL_ID, USER_ID, RentalStatus.RENTED, RENTAL_LINES, rentedAt, scheduledReturnAt, null, NOW, NOW, 0L);
        LocalDateTime returnedAt = NOW;
        DoReturnCommand command = DoReturnCommand.of(returnedAt);

        assertThatThrownBy(() -> rental.doReturn(command)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 대여를_취소한다() {
        Rental rental = RENTAL;

        rental.doCancel();

        assertThat(rental.getStatus()).isEqualTo(RentalStatus.CANCELED);
    }

    @Test
    void 취소_시_대여중인_상태가_아니면_에러를_반환한다() {
        Rental returnedRental = RETURNED_RENTAL;
        Rental canceledRental = CANCELED_RENTAL;
        assertThatThrownBy(() -> returnedRental.doCancel()).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> canceledRental.doCancel()).isInstanceOf(IllegalStateException.class);
    }
}