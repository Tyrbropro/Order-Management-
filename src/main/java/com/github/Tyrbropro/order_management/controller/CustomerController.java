package com.github.Tyrbropro.order_management.controller;

import com.github.Tyrbropro.order_management.dto.customer.CustomerRequestDTO;
import com.github.Tyrbropro.order_management.dto.customer.CustomerResponseDTO;
import com.github.Tyrbropro.order_management.service.CustomerService;
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
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Create a new customer", description = "Creates a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid customer data provided")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTO dto){
        return ResponseEntity.status(201).body(customerService.createCustomer(dto));
    }

    @Operation(summary = "Get customer by ID", description = "Returns the customer with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(
            @Parameter(description = "ID of the customer to retrieve", example = "1")
            @PathVariable @Positive Long id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @Operation(summary = "Update a customer", description = "Updates customer with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @Parameter(description = "ID of the customer to update", example = "1")
            @PathVariable @Positive Long id, @RequestBody @Valid CustomerRequestDTO details){
        return ResponseEntity.ok(customerService.updateCustomer(id,details));
    }

    @Operation(summary = "Delete a customer", description = "Deletes customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID of the customer to delete", example = "1")
            @PathVariable @Positive Long id){
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
    }
}