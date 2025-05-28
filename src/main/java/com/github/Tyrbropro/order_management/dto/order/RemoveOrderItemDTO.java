package com.github.Tyrbropro.order_management.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

public record RemoveOrderItemDTO(
        @Schema(description = "ID of the order item to remove", example = "1")
        @Positive Long orderItemId
) {}
