package com.github.Tyrbropro.order.management.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ProductResponseDTO(
        @Schema(description = "Unique ID of the product", example = "1")
        Long id,

        @Schema(description = "Name of the product", example = "Wireless Mouse")
        String name,

        @Schema(description = "Detailed description of the product",
                example = "Ergonomic wireless mouse with USB receiver")
        String description,

        @Schema(description = "Available stock quantity", example = "100")
        int stock,

        @Schema(description = "Unit price of the product", example = "29.99")
        BigDecimal price
) { }
