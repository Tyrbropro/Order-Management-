package com.github.Tyrbropro.order.management.dto.order;

import com.github.Tyrbropro.order.management.dto.order.item.OrderItemRequestDTO;
import com.github.Tyrbropro.order.management.entity.Order;
import com.github.Tyrbropro.order.management.validation.annotation.FutureOrToday;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

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
) { }
