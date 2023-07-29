package com.ys.rental.domain;

import com.ys.rental.domain.fixture.SupportRentalFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class RentalLineTest extends SupportRentalFixture {

    private static final int ZERO = 0;
    private static final int MINUS_ONE = -1;

    @Test
    void 대여_품목의_수량이_0이하이면_에러를_반환한다() {
        assertThatThrownBy(() -> RentalLine.of(RENTAL_ID, PRODUCT_ID, MONEY_1000, ZERO)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> RentalLine.of(RENTAL_ID, PRODUCT_ID, MONEY_1000, MINUS_ONE)).isInstanceOf(IllegalArgumentException.class);
    }
}