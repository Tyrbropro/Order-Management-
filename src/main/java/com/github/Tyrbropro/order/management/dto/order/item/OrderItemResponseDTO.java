package com.github.Tyrbropro.order.management.dto.order.item;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record OrderItemResponseDTO(
        @Schema(description = "Unique ID of the order item", example = "1")
        Long id,

        @Schema(description = "ID of the product being ordered", example = "1")
        long productId,

        @Schema(description = "Quantity of the product", example = "5")
        int quantity,

        @Schema(description = "Price of the product", example = "500")
        BigDecimal price
) { }