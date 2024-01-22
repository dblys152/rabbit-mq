package com.ys.rental.domain;

import com.ys.rental.domain.fixture.SupportRentalFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RentalLineTest extends SupportRentalFixture {
    @Test
    void 대여_품목의_수량이_0이하이면_에러를_반환한다() {
        assertThatThrownBy(() -> RentalLine.create(PRODUCT_ID, MONEY_1000, 0)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> RentalLine.create(PRODUCT_ID, MONEY_1000, -1)).isInstanceOf(IllegalArgumentException.class);
    }
}