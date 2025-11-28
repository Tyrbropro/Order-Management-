package com.github.Tyrbropro.order.management.dto.order.item;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record OrderItemRequestDTO (
        @Schema(description = "ID of the product being ordered", example = "1")
        @Positive long productId,

        @Schema(description = "Quantity of the product", example = "5")
        @Min(value = 1) int quantity,

        @Schema(description = "Price of the product", example = "500")
        @Positive BigDecimal price
) { }

