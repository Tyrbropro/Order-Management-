package com.github.Tyrbropro.order_management.mapper;

import com.github.Tyrbropro.order_management.dto.orderItem.OrderItemRequestDTO;
import com.github.Tyrbropro.order_management.dto.orderItem.OrderItemResponseDTO;
import com.github.Tyrbropro.order_management.entity.OrderItem;
import com.github.Tyrbropro.order_management.entity.Product;

public class OrderItemMapper {
    public static OrderItem toEntity(OrderItemRequestDTO dto, Product product) {
        return OrderItem.builder()
                .quantity(dto.quantity())
                .price(dto.price())
                .product(product)
                .build();
    }

    public static OrderItemResponseDTO toDto (OrderItem orderItem) {
        return OrderItemResponseDTO.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }

    public static void updateEntity(OrderItem orderItem, OrderItemRequestDTO dto, Product product) {
        orderItem.setQuantity(dto.quantity());
        orderItem.setPrice(dto.price());
        orderItem.setProduct(product);
    }
}
