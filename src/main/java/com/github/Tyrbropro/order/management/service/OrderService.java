package com.github.Tyrbropro.order.management.service;

import com.github.Tyrbropro.order.management.dto.order.OrderRequestDTO;
import com.github.Tyrbropro.order.management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order.management.entity.Customer;
import com.github.Tyrbropro.order.management.entity.Order;
import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(Long idCustomer, OrderRequestDTO dto);

    List<OrderResponseDTO> getAllOrdersByCustomer(Long idCustomer);

    OrderResponseDTO getOrderById(Long id, Customer currentCustomer);

    OrderResponseDTO cancelOrder(Long id, Customer currentCustomer);

    OrderResponseDTO updateStatusOrder(Long id, Order.Status status);
}
