package com.example.website_ban_trang_suc_toan_huyen.util;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidatorConstraint implements ConstraintValidator<EnumValidator, CharSequence> {
    Set<String> values;

    public EnumValidatorConstraint() {
    }

    public void initialize(EnumValidator constraintAnnotation) {
        this.values = (Set)Stream.of((Enum[])constraintAnnotation.enumClass().getEnumConstants()).map(Enum::name).collect(Collectors.toSet());
    }

    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        return value == null ? true : this.values.contains(value);
    }
}
