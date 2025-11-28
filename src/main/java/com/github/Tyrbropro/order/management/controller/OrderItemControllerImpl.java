package com.github.Tyrbropro.order.management.controller;

import com.github.Tyrbropro.order.management.dto.order.AddOrderItemDTO;
import com.github.Tyrbropro.order.management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order.management.dto.order.RemoveOrderItemDTO;
import com.github.Tyrbropro.order.management.dto.order.UpdateOrderItemQuantityDTO;
import com.github.Tyrbropro.order.management.dto.order.item.OrderItemResponseDTO;
import com.github.Tyrbropro.order.management.entity.Customer;
import com.github.Tyrbropro.order.management.service.OrderItemServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderItemControllerImpl implements OrderItemController {

    private final OrderItemServiceImpl orderItemServiceImpl;

    public OrderItemControllerImpl(OrderItemServiceImpl orderItemServiceImpl) {
        this.orderItemServiceImpl = orderItemServiceImpl;
    }

    @Override
    public ResponseEntity<OrderItemResponseDTO> addItemToOrder(Long orderId, AddOrderItemDTO dto, Customer currentCustomer) {
        return ResponseEntity.status(201).body(orderItemServiceImpl.addItemToOrder(orderId, dto, currentCustomer));
    }

    @Override
    public ResponseEntity<OrderResponseDTO> removeItemFromOrder(Long orderId, RemoveOrderItemDTO dto, Customer currentCustomer) {
        orderItemServiceImpl.removeItemFromOrder(orderId, dto, currentCustomer);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<OrderResponseDTO> updateItemQuantity(Long orderId, UpdateOrderItemQuantityDTO dto, Customer currentCustomer) {
            return ResponseEntity.ok(orderItemServiceImpl.updateItemQuantity(orderId, dto, currentCustomer));
    }
}