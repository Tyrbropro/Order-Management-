package com.github.Tyrbropro.order.management.service;

import com.github.Tyrbropro.order.management.dto.order.AddOrderItemDTO;
import com.github.Tyrbropro.order.management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order.management.dto.order.RemoveOrderItemDTO;
import com.github.Tyrbropro.order.management.dto.order.UpdateOrderItemQuantityDTO;
import com.github.Tyrbropro.order.management.dto.order.item.OrderItemResponseDTO;
import com.github.Tyrbropro.order.management.entity.Customer;
import com.github.Tyrbropro.order.management.entity.Order;
import com.github.Tyrbropro.order.management.entity.OrderItem;
import com.github.Tyrbropro.order.management.entity.Product;
import com.github.Tyrbropro.order.management.exception.OutOfStockException;
import com.github.Tyrbropro.order.management.mapper.OrderItemMapper;
import com.github.Tyrbropro.order.management.mapper.OrderMapper;
import com.github.Tyrbropro.order.management.repository.OrderRepository;
import com.github.Tyrbropro.order.management.repository.ProductRepository;
import com.github.Tyrbropro.order.management.util.Checking;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemServiceImpl implements OrderItemService {

    OrderRepository orderRepository;

    ProductRepository productRepository;

    OrderServiceImpl orderServiceImpl;

    public OrderItemServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                                OrderServiceImpl orderServiceImpl) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderServiceImpl = orderServiceImpl;
    }

    @Override
    public OrderItemResponseDTO addItemToOrder(Long id, AddOrderItemDTO dto, Customer currentCustomer) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        Checking.checkCurrentCustomer(currentCustomer, order);

        if (product.getStock() < dto.quantity()) {
            throw new OutOfStockException("Not enough stock for product: " + product.getName());
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(dto.quantity());
        orderItem.setPrice(product.getPrice());

        order.getItems().add(orderItem);

        product.setStock(product.getStock() - dto.quantity());

        productRepository.save(product);
        orderRepository.save(order);


        orderServiceImpl.calculateTotalAmount(order);

        return OrderItemMapper.toDto(orderItem);
    }

    @Override
    public void removeItemFromOrder(Long orderId, RemoveOrderItemDTO dto, Customer currentCustomer) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Checking.checkCurrentCustomer(currentCustomer, order);

        boolean removed = order.getItems().removeIf(item -> item.getId().equals(dto.orderItemId()));

        if (!removed) {
            throw new EntityNotFoundException("OrderItem not found");
        }

        orderServiceImpl.calculateTotalAmount(order);
    }

    @Override
    public OrderResponseDTO updateItemQuantity(Long orderId, UpdateOrderItemQuantityDTO dto, Customer currentCustomer) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        Checking.checkCurrentCustomer(currentCustomer, order);

        OrderItem orderItem = order.getItems().stream()
                .filter(item -> item.getId().equals(dto.itemOrderId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found"));

        int oldQuantity = orderItem.getQuantity();
        int difference = dto.newQuantity() - oldQuantity;

        Product product = orderItem.getProduct();

        if (difference > 0 && product.getStock() < difference) {
            throw new OutOfStockException("Not enough stock for product: " + product.getName());
        }

        orderItem.setQuantity(dto.newQuantity());
        product.setStock(product.getStock() - difference);
        productRepository.save(product);

        orderServiceImpl.calculateTotalAmount(order);
        return OrderMapper.toDto(orderRepository.save(order));
    }
}