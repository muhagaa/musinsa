package com.musinsa.item.validator;

import com.musinsa.item.annotation.ValidCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;


public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {
    private final List<String> validCategories = List.of("상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "악세서리");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && validCategories.contains(value);
    }
}
