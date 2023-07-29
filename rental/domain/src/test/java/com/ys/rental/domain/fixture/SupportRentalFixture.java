package com.ys.rental.domain.fixture;

import com.ys.product.domain.product.ProductId;
import com.ys.rental.domain.*;
import com.ys.rental.refs.user.domain.UserId;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SupportRentalFixture {

    protected static final LocalDateTime NOW = LocalDateTime.now();
    protected static final RentalId RENTAL_ID = RentalId.of("RENTAL_ID");
    protected static final UserId USER_ID = UserId.of("USER_ID");
    protected static final ProductId PRODUCT_ID = ProductId.of("PRODUCT_ID");
    protected static final ProductId PRODUCT_ID_2 = ProductId.of("PRODUCT_ID_2");
    protected static final Money MONEY_1000 = Money.of(1000);
    protected static final int QUANTITY_ONE = 1;
    protected static final int QUANTITY_TWO = 2;
    protected static final RentalLine RENTAL_LINE = RentalLine.of(RENTAL_ID, PRODUCT_ID, MONEY_1000, QUANTITY_ONE);
    protected static final RentalLine RENTAL_LINE_2 = RentalLine.of(RENTAL_ID, PRODUCT_ID_2, MONEY_1000, QUANTITY_TWO);
    protected static final RentalLines RENTAL_LINES = RentalLines.of(Arrays.asList(RENTAL_LINE, RENTAL_LINE_2));
    protected static final CreateRentalCommand CREATE_RENTAL_COMMAND = CreateRentalCommand.of(
            RENTAL_ID, USER_ID, RENTAL_LINES, NOW, NOW.plusHours(3));
    protected static final Rental RENTAL = Rental.of(
            RENTAL_ID, USER_ID, RentalStatus.RENTED, RENTAL_LINES, NOW, NOW.plusHours(3), null, NOW, NOW, 0L);
    protected static final Rental RETURNED_RENTAL = Rental.of(
            RENTAL_ID, USER_ID, RentalStatus.RETURNED, RENTAL_LINES, NOW, NOW.plusHours(3), NOW.plusHours(2), NOW, NOW, 0L);
    protected static final Rental CANCELED_RENTAL = Rental.of(
            RENTAL_ID, USER_ID, RentalStatus.CANCELED, RENTAL_LINES, NOW, NOW.plusHours(3), null, NOW, NOW, 0L);
}
