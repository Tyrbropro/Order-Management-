package com.github.Tyrbropro.order_management.controller;

import com.github.Tyrbropro.order_management.dto.order.AddOrderItemDTO;
import com.github.Tyrbropro.order_management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order_management.dto.order.RemoveOrderItemDTO;
import com.github.Tyrbropro.order_management.dto.order.UpdateOrderItemQuantityDTO;
import com.github.Tyrbropro.order_management.dto.orderItem.OrderItemResponseDTO;
import com.github.Tyrbropro.order_management.entity.Customer;
import com.github.Tyrbropro.order_management.service.OrderItemService;
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

@Tag(name = "OrderItems", description = "APIs for managing order items")
@RestController
@RequestMapping("/orders/{orderId}/items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }


    @Operation(summary = "Add item to order",
            description = "Adds a new item to the specified order. Accessible by customers and admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item added to order successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid item data provided")
    })
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<OrderItemResponseDTO> addItemToOrder(
            @Parameter(description = "ID of the order", example = "1")
            @PathVariable @Positive Long orderId, @RequestBody @Valid AddOrderItemDTO dto,
            @AuthenticationPrincipal Customer currentCustomer) {
        return ResponseEntity.status(201).body(orderItemService.addItemToOrder(orderId, dto, currentCustomer));
    }

    @Operation(summary = "Remove item from order",
            description = "Removes an item from the specified order. Accessible by customers and admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removed from order successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @DeleteMapping("")
    public ResponseEntity<OrderResponseDTO> removeItemFromOrder(
            @Parameter(description = "ID of the order", example = "1")
            @PathVariable @Positive Long orderId, @RequestBody @Valid RemoveOrderItemDTO dto,
            @AuthenticationPrincipal Customer currentCustomer) {
        orderItemService.removeItemFromOrder(orderId, dto, currentCustomer);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Update item quantity",
    description = "Updates the quantity of an existing item in the order. Accessible by customers and admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item quantity updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid quantity value"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @PutMapping("")
    public ResponseEntity<OrderResponseDTO> updateItemQuantity(
            @Parameter(description = "ID of the order", example = "1")
            @PathVariable @Positive Long orderId, @RequestBody @Valid UpdateOrderItemQuantityDTO dto,
            @AuthenticationPrincipal Customer currentCustomer) {
            return ResponseEntity.ok(orderItemService.updateItemQuantity(orderId, dto, currentCustomer));
    }
}