package com.example.website_ban_trang_suc_toan_huyen.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(
        validatedBy = {EnumValidatorConstraint.class}
)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface EnumValidator {
    Class<? extends Enum<?>> enumClass();

    String message() default "must be any of enum {enum}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}