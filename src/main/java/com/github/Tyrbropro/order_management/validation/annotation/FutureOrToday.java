package com.github.Tyrbropro.order_management.validation.annotation;

import com.github.Tyrbropro.order_management.validation.validator.FutureOrTodayValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {FutureOrTodayValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureOrToday {
    String message() default "The date cannot be in the past";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
