package com.github.Tyrbropro.order_management.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record UpdateOrderItemQuantityDTO(
        @Schema(description = "ID of the item order to update", example = "1")
        @Positive Long itemOrderId,

        @Schema(description = "New quantity for the product", example = "5")
        @Positive int newQuantity
) {}
