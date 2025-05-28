package com.github.Tyrbropro.order_management.dto.order;

import com.github.Tyrbropro.order_management.dto.orderItem.OrderItemRequestDTO;
import com.github.Tyrbropro.order_management.entity.Order;
import com.github.Tyrbropro.order_management.validation.annotation.FutureOrToday;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Builder
public record OrderRequestDTO (
    @Schema(description = "Date and time when the order was placed (must be today or in the future)",
    example = "2025-05-20T23:30:00")
    @NotNull @FutureOrToday LocalDateTime orderDate,

    @Schema(description = "Current status of the order", example = "NEW")
    @NotNull Order.Status status,

    @Schema(description = "Total cost of the order", example = "149.99")
    @Positive BigDecimal totalAmount,

    @Schema(description = "List of items included in the order")
    @Valid List<OrderItemRequestDTO> orderItems
){}
