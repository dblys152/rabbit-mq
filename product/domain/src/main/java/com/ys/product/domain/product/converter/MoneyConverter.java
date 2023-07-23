package com.ys.product.domain.product.converter;

import com.ys.product.domain.product.Money;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Money money) {
        return money != null ? money.getValue() : null;
    }

    @Override
    public Money convertToEntityAttribute(Integer money) {
        return money != null ? Money.of(money) : null;
    }
}
