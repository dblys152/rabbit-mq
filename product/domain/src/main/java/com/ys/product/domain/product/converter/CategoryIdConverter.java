package com.ys.product.domain.product.converter;

import com.ys.product.refs.category.domain.CategoryId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryIdConverter implements AttributeConverter<CategoryId, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CategoryId categoryId) {
        return categoryId != null ? categoryId.getId() : null;
    }

    @Override
    public CategoryId convertToEntityAttribute(Integer categoryId) {
        return categoryId != null ? CategoryId.of(categoryId) : null;
    }
}
