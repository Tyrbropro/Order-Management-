package com.github.Tyrbropro.order.management.dto.order;

import com.github.Tyrbropro.order.management.dto.customer.CustomerShortDTO;
import com.github.Tyrbropro.order.management.dto.order.item.OrderItemResponseDTO;
import com.github.Tyrbropro.order.management.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record OrderResponseDTO (
    @Schema(description = "Unique ID of the order", example = "1")
    long id,

    @Schema(description = "Customer who placed the order")
    CustomerShortDTO customer,

    @Schema(description = "Date and time when the order was placed (must be today or in the future)",
            example = "2025-05-20T23:30:00")
    LocalDateTime orderDate,

    @Schema(description = "Current status of the order", example = "NEW")
    Order.Status status,

    @Schema(description = "Total cost of the order", example = "149.99")
    BigDecimal totalAmount,

    @Schema(description = "List of items included in the order")
    List<OrderItemResponseDTO> orderItems
) { }