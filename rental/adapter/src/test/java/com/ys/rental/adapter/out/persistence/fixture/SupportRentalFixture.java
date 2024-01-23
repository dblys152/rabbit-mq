package com.ys.rental.adapter.out.persistence.fixture;

import com.ys.rental.domain.*;
import com.ys.rental.refs.product.domain.ProductId;
import com.ys.rental.refs.user.domain.UserId;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SupportRentalFixture {
    protected static final LocalDateTime NOW = LocalDateTime.now();
    protected static final RentalId RENTAL_ID = RentalId.of(999999999L);
    protected static final UserId USER_ID = UserId.of(123L);
    protected static final ProductId PRODUCT_ID = ProductId.of(777L);
    protected static final ProductId PRODUCT_ID_2 = ProductId.of(778L);
    protected static final Money MONEY_1000 = Money.of(1000);
    protected static final int QUANTITY_ONE = 1;
    protected static final int QUANTITY_TWO = 2;
    protected static final RentalLine RENTAL_LINE = RentalLine.create(PRODUCT_ID, MONEY_1000, QUANTITY_ONE);
    protected static final RentalLine RENTAL_LINE_2 = RentalLine.create(PRODUCT_ID_2, MONEY_1000, QUANTITY_TWO);
    protected static final RentalLines RENTAL_LINES = RentalLines.of(Arrays.asList(RENTAL_LINE, RENTAL_LINE_2));
    protected static final RentalPeriod RENTAL_PERIOD = RentalPeriod.of(NOW, NOW.plusHours(3));
}
