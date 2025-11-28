package com.github.Tyrbropro.order.management.controller;

import com.github.Tyrbropro.order.management.dto.customer.CustomerRequestDTO;
import com.github.Tyrbropro.order.management.dto.customer.CustomerResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customers", description = "APIs for managing customer")
@RequestMapping("/customers")
public interface CustomerController {

    @Operation(summary = "Create a new customer", description = "Creates a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid customer data provided")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTO dto);

    @Operation(summary = "Get customer by ID", description = "Returns the customer with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    ResponseEntity<CustomerResponseDTO> getCustomerById(
            @Parameter(description = "ID of the customer to retrieve", example = "1")
            @PathVariable @Positive Long id);

    @Operation(summary = "Update a customer", description = "Updates customer with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<CustomerResponseDTO> updateCustomer(
            @Parameter(description = "ID of the customer to update", example = "1")
            @PathVariable @Positive Long id, @RequestBody @Valid CustomerRequestDTO details);

    @Operation(summary = "Delete a customer", description = "Deletes customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID of the customer to delete", example = "1")
            @PathVariable @Positive Long id);
}
