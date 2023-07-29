package com.ys.rental.domain;

import com.ys.rental.domain.fixture.SupportRentalFixture;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class RentalLinesTest extends SupportRentalFixture {

    @Test
    void 대여_상품을_중복해서_넣으면_에러를_반환한다() {
        RentalLine rentalLine = RentalLine.of(RENTAL_ID, PRODUCT_ID, MONEY_1000, QUANTITY_ONE);
        RentalLine rentalLine2 = RentalLine.of(RENTAL_ID, PRODUCT_ID, MONEY_1000, QUANTITY_TWO);
        assertThatThrownBy(() -> RentalLines.of(Arrays.asList(rentalLine, rentalLine2)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}