package com.github.Tyrbropro.order.management.dto.customer;

import com.github.Tyrbropro.order.management.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CustomerShortDTO(
        @Schema(description = "Unique ID of the customer", example = "1")
        Long id,

        @Schema(description = "Customer's full name", example = "John Smith")
        String name,

        @Schema(description = "Customer's email address", example = "john@example.com")
        String email,

        @Schema(description = "Customer's residential address", example = "123 Main St, Springfield")
        String address,

        @Schema(description = "Password used for login", example = "securePassword123")
        String password,

        @Schema(description = "Role assigned to the customer", example = "CUSTOMER")
        Customer.Role role
) { }
