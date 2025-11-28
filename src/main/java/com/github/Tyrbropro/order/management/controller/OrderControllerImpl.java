package com.github.Tyrbropro.order.management.controller;

import com.github.Tyrbropro.order.management.dto.order.OrderRequestDTO;
import com.github.Tyrbropro.order.management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order.management.dto.order.OrderStatusUpdateDTO;
import com.github.Tyrbropro.order.management.entity.Customer;
import com.github.Tyrbropro.order.management.service.OrderServiceImpl;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderControllerImpl implements OrderController {

    private final OrderServiceImpl orderServiceImpl;

    public OrderControllerImpl(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @Override
    public ResponseEntity<OrderResponseDTO> createOrder(OrderRequestDTO dto, Customer currentCustomer) {
        OrderResponseDTO response = orderServiceImpl.createOrder(currentCustomer.getId(), dto);
        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<List<OrderResponseDTO>> myOrders(Customer currentCustomer) {
        return ResponseEntity.ok(orderServiceImpl.getAllOrdersByCustomer(currentCustomer.getId()));
    }

    @Override
    public ResponseEntity<OrderResponseDTO> getOrderById(Customer currentCustomer, Long id) {
        return ResponseEntity.ok(orderServiceImpl.getOrderById(id, currentCustomer));
    }

    @Override
    public ResponseEntity<OrderResponseDTO> cancelOrder(Long id, Customer currentCustomer) {
            return ResponseEntity.ok(orderServiceImpl.cancelOrder(id, currentCustomer));
    }

    @Override
    public ResponseEntity<OrderResponseDTO> updateStatusOrder(Long id, OrderStatusUpdateDTO dto) {
            return ResponseEntity.ok(orderServiceImpl.updateStatusOrder(id, dto.status()));
    }
}