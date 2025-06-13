package com.github.Tyrbropro.order.management.controller;

import com.github.Tyrbropro.order.management.dto.order.AddOrderItemDTO;
import com.github.Tyrbropro.order.management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order.management.dto.order.RemoveOrderItemDTO;
import com.github.Tyrbropro.order.management.dto.order.UpdateOrderItemQuantityDTO;
import com.github.Tyrbropro.order.management.dto.order.item.OrderItemResponseDTO;
import com.github.Tyrbropro.order.management.entity.Customer;
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
@RequestMapping("/orders/{orderId}/items")
public interface OrderItemController {

    @Operation(summary = "Add item to order",
            description = "Adds a new item to the specified order. Accessible by customers and admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item added to order successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid item data provided")
    })
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @PostMapping("")
    ResponseEntity<OrderItemResponseDTO> addItemToOrder(
            @Parameter(description = "ID of the order", example = "1")
            @PathVariable @Positive Long orderId, @RequestBody @Valid AddOrderItemDTO dto,
            @AuthenticationPrincipal Customer currentCustomer);

    @Operation(summary = "Remove item from order",
            description = "Removes an item from the specified order. Accessible by customers and admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removed from order successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    @DeleteMapping("")
    ResponseEntity<OrderResponseDTO> removeItemFromOrder(
            @Parameter(description = "ID of the order", example = "1")
            @PathVariable @Positive Long orderId, @RequestBody @Valid RemoveOrderItemDTO dto,
            @AuthenticationPrincipal Customer currentCustomer);


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
    ResponseEntity<OrderResponseDTO> updateItemQuantity(
            @Parameter(description = "ID of the order", example = "1")
            @PathVariable @Positive Long orderId, @RequestBody @Valid UpdateOrderItemQuantityDTO dto,
            @AuthenticationPrincipal Customer currentCustomer);
}
