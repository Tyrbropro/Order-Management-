package com.github.Tyrbropro.order_management.validation.validator;

import com.github.Tyrbropro.order_management.repository.ProductRepository;
import com.github.Tyrbropro.order_management.validation.annotation.UniqueProductName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {

    private final ProductRepository productRepository;

    public UniqueProductNameValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean isValid(String productName, ConstraintValidatorContext context) {
        return productName.isBlank() || !productRepository.existsByName(productName);
    }
}
