package com.ys.rental.domain;

import lombok.Value;

import java.util.List;

@Value(staticConstructor = "of")
public class RentalLines {

    List<RentalLine> items;
}
