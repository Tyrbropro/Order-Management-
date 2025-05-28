package com.github.Tyrbropro.order_management.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequestDTO(
        @Schema(description = "Name of the product", example = "Wireless Mouse")
        @NotBlank String name,

        @Schema(description = "Detailed description of the product",
                example = "Ergonomic wireless mouse with USB receiver")
        @NotBlank String description,

        @Schema(description = "Available stock quantity", example = "100")
        @Positive int stock,

        @Schema(description = "Unit price of the product", example = "29.99")
        @Positive @Min(value = 1) BigDecimal price
) {}
