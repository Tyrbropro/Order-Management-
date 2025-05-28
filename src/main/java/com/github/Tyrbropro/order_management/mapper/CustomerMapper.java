package com.github.Tyrbropro.order_management.mapper;

import com.github.Tyrbropro.order_management.dto.customer.CustomerRequestDTO;
import com.github.Tyrbropro.order_management.dto.customer.CustomerResponseDTO;
import com.github.Tyrbropro.order_management.dto.customer.CustomerShortDTO;
import com.github.Tyrbropro.order_management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order_management.entity.Customer;
import com.github.Tyrbropro.order_management.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static Customer toEntity(CustomerRequestDTO dto, List<Order> orders){
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setAddress(dto.address());
        customer.setPassword(dto.password());
        customer.setRole(dto.role());

        orders.forEach(order -> order.setCustomer(customer));
        customer.setOrders(orders);

        return customer;
    }

    public static CustomerResponseDTO toDto(Customer customer){
        List<OrderResponseDTO> orderDtos = customer.getOrders() != null
                ? customer.getOrders().stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList())
                : List.of();

        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .password(customer.getPassword())
                .role(customer.getRole())
                .orders(orderDtos)
                .build();
    }

    public static CustomerShortDTO toShortDto(Customer customer) {
        return CustomerShortDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .password(customer.getPassword())
                .role(customer.getRole())
                .build();
    }

    public static void updateEntity(Customer customer, CustomerRequestDTO dto, List<Order> orders){
        customer.setName(dto.name());
        customer.setEmail(dto.email());
        customer.setAddress(dto.address());
        customer.setPassword(dto.password());
        customer.setRole(dto.role());
        customer.setOrders(orders);
    }
}

