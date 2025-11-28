package com.github.Tyrbropro.order.management.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record AddOrderItemDTO (
        @Schema(description = "ID of the product being ordered", example = "1")
        @Positive Long productId,

        @Schema(description = "Quantity of the product", example = "5")
        @Min(value = 1) int quantity
) { }
