package com.ys.rental.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Money {

    int value;
}
