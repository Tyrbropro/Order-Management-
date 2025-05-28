package com.github.Tyrbropro.order_management.validation.validator;

import com.github.Tyrbropro.order_management.validation.annotation.FutureOrToday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FutureOrTodayValidator implements ConstraintValidator<FutureOrToday, LocalDateTime> {


    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        return localDateTime == null || !localDateTime.isBefore(LocalDateTime.now());
    }
}
