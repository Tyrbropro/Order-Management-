package com.github.Tyrbropro.order_management.dto.order;

import com.github.Tyrbropro.order_management.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateDTO(
        @Schema(description = "New status to set for the order",
                example = "NEW")
       @NotNull Order.Status status
) { }
