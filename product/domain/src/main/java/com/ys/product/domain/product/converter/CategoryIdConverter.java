package com.ys.product.domain.product.converter;

import com.ys.product.refs.category.CategoryId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryIdConverter implements AttributeConverter<CategoryId, String> {

    @Override
    public String convertToDatabaseColumn(CategoryId categoryId) {
        return categoryId != null ? categoryId.getId() : null;
    }

    @Override
    public CategoryId convertToEntityAttribute(String categoryId) {
        return categoryId != null ? CategoryId.of(categoryId) : null;
    }
}
