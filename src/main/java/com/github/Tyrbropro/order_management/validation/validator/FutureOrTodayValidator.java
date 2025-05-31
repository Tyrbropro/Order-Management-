package com.github.Tyrbropro.order_management.validation.validator;

import com.github.Tyrbropro.order_management.validation.annotation.FutureOrToday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class FutureOrTodayValidator implements ConstraintValidator<FutureOrToday, LocalDateTime> {


    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        return localDateTime == null || !localDateTime.isBefore(LocalDateTime.now());
    }
}
