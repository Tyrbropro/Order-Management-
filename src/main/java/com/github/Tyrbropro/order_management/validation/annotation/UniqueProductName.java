package com.github.Tyrbropro.order_management.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.hv.UniqueElementsValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UniqueElementsValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueProductName {
    String message() default "There is already such a user";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
