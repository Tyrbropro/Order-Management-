package com.github.Tyrbropro.order.management.dto.customer;

import com.github.Tyrbropro.order.management.dto.order.OrderRequestDTO;
import com.github.Tyrbropro.order.management.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record CustomerRequestDTO (
    @Schema(description = "Customer's full name", example = "John Smith")
    @NotBlank String name,

    @Schema(description = "Customer's email address", example = "john@example.com")
    @Email String email,

    @Schema(description = "Customer's residential address", example = "123 Main St, Springfield")
    @NotBlank String address,

    @Schema(description = "Password used for login", example = "securePassword123")
    @NotBlank String password,

    @Schema(description = "Role assigned to the customer", example = "CUSTOMER")
    @NotNull Customer.Role role,

    @Schema(description = "List of initial orders placed by the customer")
    @Valid List<OrderRequestDTO> orders
) { }