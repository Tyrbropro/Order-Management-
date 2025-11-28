package com.github.Tyrbropro.order.management.controller;

import com.github.Tyrbropro.order.management.dto.order.OrderRequestDTO;
import com.github.Tyrbropro.order.management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order.management.dto.order.OrderStatusUpdateDTO;
import com.github.Tyrbropro.order.management.entity.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Orders", description = "APIs for managing customer orders")
@RequestMapping("/orders")
public interface OrderController {

    @Operation(summary = "Create a new order", description = "Creates a new order for the authenticated customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order data provided")
    })
    @PostMapping("")
    ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderRequestDTO dto,
                                                        @AuthenticationPrincipal Customer currentCustomer);

    @Operation(summary = "Get current customer's orders",
            description = "Retrieves all orders placed by the authenticated customer")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @GetMapping("/my")
    ResponseEntity<List<OrderResponseDTO>> myOrders(@AuthenticationPrincipal Customer currentCustomer);

    @Operation(summary = "Get order by ID",
            description = "Retrieves the order with the specified ID. Accessible by customers and admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    ResponseEntity<OrderResponseDTO> getOrderById(@AuthenticationPrincipal Customer currentCustomer,
                                                         @Parameter(description = "ID of the order to retrieve",
                                                                 example = "1")
                                                         @PathVariable @Positive Long id);

    @Operation(summary = "Cancel order",
            description = "Cancels an existing order. Only customers can cancel their orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order cancel"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{id}/cancel")
    ResponseEntity<OrderResponseDTO> cancelOrder(
            @Parameter(description = "ID of the order to cancel", example = "1")
            @PathVariable Long id, @AuthenticationPrincipal Customer currentCustomer);

    @Operation(summary = "Update order status",
            description = "Updates the status of an existing order. Only admin can perform this operation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status value"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    ResponseEntity<OrderResponseDTO> updateStatusOrder(
            @Parameter(description = "ID of the order to update", example = "1")
            @PathVariable Long id, @RequestBody @Valid OrderStatusUpdateDTO dto);
}
