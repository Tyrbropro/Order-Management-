package com.github.Tyrbropro.order.management.service;

import com.github.Tyrbropro.order.management.dto.order.AddOrderItemDTO;
import com.github.Tyrbropro.order.management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order.management.dto.order.RemoveOrderItemDTO;
import com.github.Tyrbropro.order.management.dto.order.UpdateOrderItemQuantityDTO;
import com.github.Tyrbropro.order.management.dto.order.item.OrderItemResponseDTO;
import com.github.Tyrbropro.order.management.entity.Customer;

public interface OrderItemService {

    OrderItemResponseDTO addItemToOrder(Long id, AddOrderItemDTO dto, Customer currentCustomer);

    void removeItemFromOrder(Long orderId, RemoveOrderItemDTO dto, Customer currentCustomer);

    OrderResponseDTO updateItemQuantity(Long orderId, UpdateOrderItemQuantityDTO dto, Customer currentCustomer);
}
