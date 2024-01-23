package com.ys.rental.domain;

import com.ys.rental.domain.fixture.SupportRentalFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class RentalTest extends SupportRentalFixture {
    private Rental rental;
    private Rental reservedRental;
    private Rental returnedRental;
    private Rental canceledRental;

    @BeforeEach
    void setUp() {
        rental = Rental.of(
                RENTAL_ID, USER_ID, RentalStatus.RENTED, RENTAL_LINES, RENTAL_PERIOD, null, NOW, NOW, 0L);
        reservedRental = Rental.of(
                RENTAL_ID, USER_ID, RentalStatus.RENTED, RENTAL_LINES, RESERVED_RENTAL_PERIOD, null, NOW, NOW, 0L);
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
    void 대여_취소한다() {
        reservedRental.doCancel();

        assertThat(reservedRental.getStatus()).isEqualTo(RentalStatus.CANCELED);
    }

    @Test
    void 취소_시_대여중인_상태가_아니면_에러를_반환한다() {
        assertThatThrownBy(() -> returnedRental.doCancel()).isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> canceledRental.doCancel()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 취소_시_대여_시작_시간이_지나_이미_대여_중이면_에러를_반환한다() {
        assertThatThrownBy(() -> rental.doCancel()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 대여_반납한다() {
        rental.doReturn();

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
}