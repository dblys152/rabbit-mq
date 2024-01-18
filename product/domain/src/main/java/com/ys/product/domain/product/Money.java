package com.ys.product.domain.product;

import lombok.Value;

@Value(staticConstructor = "of")
public class Money {
    int value;
}
