package com.github.Tyrbropro.order_management.util;

import com.github.Tyrbropro.order_management.dto.customer.CustomerRequestDTO;
import com.github.Tyrbropro.order_management.dto.order.OrderRequestDTO;
import com.github.Tyrbropro.order_management.dto.orderItem.OrderItemRequestDTO;
import com.github.Tyrbropro.order_management.dto.product.ProductRequestDTO;
import com.github.Tyrbropro.order_management.entity.Customer;
import com.github.Tyrbropro.order_management.entity.Order;
import com.github.Tyrbropro.order_management.entity.OrderItem;
import com.github.Tyrbropro.order_management.entity.Product;
import com.github.Tyrbropro.order_management.mapper.CustomerMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class TestDataFactory {

    public static Customer createCustomer(Long id) {
        return Customer.builder()
                .id(id)
                .name("Test Name")
                .email("TestEmail@example.com")
                .address("Test Address")
                .password("TestPassword")
                .role(Customer.Role.CUSTOMER)
                .orders(new ArrayList<>())
                .build();
    }

    public static Product createProduct(Long id) {
        return Product.builder()
                .id(id)
                .name("Test Product")
                .description("Test Product Description")
                .price(new BigDecimal("50.00"))
                .stock(10)
                .build();
    }

    public static OrderItem createOrderItem(Long id, Long productId) {
        return OrderItem.builder()
                .id(id)
                .product(createProduct(productId))
                .quantity(3)
                .price(new BigDecimal("50.00"))
                .build();
    }

    public static Order createOrder(Long orderId, Long customerId, Long orderItemId, Long productId) {
        return Order.builder()
                .id(orderId)
                .customer(createCustomer(customerId))
                .orderDate(LocalDateTime.of(2015, Month.JUNE, 15, 12, 15))
                .status(Order.Status.NEW)
                .totalAmount(new BigDecimal("9999.99"))
                .items(List.of(createOrderItem(orderItemId, productId)))
                .build();
    }

    public static OrderItemRequestDTO createOrderItemRequestDTO(Product product) {
        return OrderItemRequestDTO.builder()
                .productId(product.getId())
                .quantity(3)
                .price(product.getPrice())
                .build();
    }

    public static OrderRequestDTO createOrderRequestDTO(Product product) {
        return OrderRequestDTO.builder()
                .orderDate(LocalDateTime.of(2015, Month.JUNE, 15, 12, 15))
                .status(Order.Status.NEW)
                .totalAmount(new BigDecimal("9999.99"))
                .orderItems(List.of(createOrderItemRequestDTO(product)))
                .build();
    }

    public static ProductRequestDTO createProductRequestDTO() {
        return ProductRequestDTO.builder()
                .description("Test Product Description")
                .name("Test Product Name")
                .stock(10)
                .price(new BigDecimal("50.00"))
                .build();
    }

    public static CustomerRequestDTO createCustomerRequestDTO() {
        return CustomerRequestDTO.builder()
                .name("Test Customer Name")
                .email("TestEmail@example.com")
                .address("Test Address")
                .password("TestPassword")
                .role(Customer.Role.CUSTOMER)
                .build();
    }
}
