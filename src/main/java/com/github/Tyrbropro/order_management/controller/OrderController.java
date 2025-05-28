package com.github.Tyrbropro.order_management.controller;

import com.github.Tyrbropro.order_management.dto.order.OrderRequestDTO;
import com.github.Tyrbropro.order_management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order_management.dto.order.OrderStatusUpdateDTO;
import com.github.Tyrbropro.order_management.entity.Customer;
import com.github.Tyrbropro.order_management.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders", description = "APIs for managing customer orders")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create a new order", description = "Creates a new order for the authenticated customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order data provided")
    })
    @PostMapping("")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody @Valid OrderRequestDTO dto, @AuthenticationPrincipal Customer currentCustomer) {
        OrderResponseDTO response = orderService.createOrder(currentCustomer.getId(), dto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Get current customer's orders",
            description = "Retrieves all orders placed by the authenticated customer")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully")
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponseDTO>> myOrders(@AuthenticationPrincipal Customer currentCustomer) {
        return ResponseEntity.ok(orderService.getAllOrdersByCustomer(currentCustomer.getId()));
    }

    @Operation(summary = "Get order by ID",
            description = "Retrieves the order with the specified ID. Accessible by customers and admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@AuthenticationPrincipal Customer currentCustomer,
                                                         @Parameter(description = "ID of the order to retrieve", example = "1")
                                                         @PathVariable @Positive Long id){
        return ResponseEntity.ok(orderService.getOrderById(id,currentCustomer));
    }

    @Operation(summary = "Cancel order",
            description = "Cancels an existing order. Only customers can cancel their orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order cancel"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(
            @Parameter(description = "ID of the order to cancel", example = "1")
            @PathVariable Long id, @AuthenticationPrincipal Customer currentCustomer){
            return ResponseEntity.ok(orderService.cancelOrder(id, currentCustomer));
    }

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
    public ResponseEntity<OrderResponseDTO> updateStatusOrder(
            @Parameter(description = "ID of the order to update", example = "1")
            @PathVariable Long id, @RequestBody @Valid OrderStatusUpdateDTO dto){
            return ResponseEntity.ok(orderService.updateStatusOrder(id, dto.status()));
    }
}