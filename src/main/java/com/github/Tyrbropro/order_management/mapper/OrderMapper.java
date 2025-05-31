package com.github.Tyrbropro.order_management.mapper;

import com.github.Tyrbropro.order_management.dto.order.OrderRequestDTO;
import com.github.Tyrbropro.order_management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order_management.dto.orderItem.OrderItemResponseDTO;
import com.github.Tyrbropro.order_management.entity.Customer;
import com.github.Tyrbropro.order_management.entity.Order;
import com.github.Tyrbropro.order_management.entity.OrderItem;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toEntity(OrderRequestDTO dto, Customer customer, List<OrderItem> items) {
        return Order.builder()
                .customer(customer)
                .orderDate(dto.orderDate())
                .status(dto.status())
                .totalAmount(dto.totalAmount())
                .items(items)
                .build();
    }

    public static Order toEntity(OrderRequestDTO dto, Customer customer) {
        return Order.builder()
                .customer(customer)
                .orderDate(dto.orderDate())
                .status(dto.status())
                .totalAmount(dto.totalAmount())
                .build();
    }

    public static OrderResponseDTO toDto(Order order) {
        List<OrderItemResponseDTO> itemDtos = order.getItems().stream()
                .map(OrderItemMapper::toDto)
                .collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .id(order.getId())
                .customer(CustomerMapper.toShortDto(order.getCustomer()))
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .orderItems(itemDtos)
                .build();
    }

    public static void updateEntity(Order order, OrderRequestDTO dto, List<OrderItem> items) {
        order.setOrderDate(dto.orderDate());
        order.setStatus(dto.status());
        order.setTotalAmount(dto.totalAmount());
        order.setItems(items);
    }
}
