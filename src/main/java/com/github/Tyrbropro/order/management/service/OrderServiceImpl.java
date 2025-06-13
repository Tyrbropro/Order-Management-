package com.github.Tyrbropro.order.management.service;

import com.github.Tyrbropro.order.management.dto.order.OrderRequestDTO;
import com.github.Tyrbropro.order.management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order.management.entity.Customer;
import com.github.Tyrbropro.order.management.entity.Order;
import com.github.Tyrbropro.order.management.entity.OrderItem;
import com.github.Tyrbropro.order.management.entity.Product;
import com.github.Tyrbropro.order.management.exception.OutOfStockException;
import com.github.Tyrbropro.order.management.mapper.OrderItemMapper;
import com.github.Tyrbropro.order.management.mapper.OrderMapper;
import com.github.Tyrbropro.order.management.repository.CustomerRepository;
import com.github.Tyrbropro.order.management.repository.OrderRepository;
import com.github.Tyrbropro.order.management.repository.ProductRepository;
import com.github.Tyrbropro.order.management.util.Checking;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    CustomerRepository customerRepository;

    ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(Long idCustomer, OrderRequestDTO dto) {
        Customer customer = customerRepository.findById(idCustomer)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);

        List<OrderItem> items = dto.orderItems().stream()
                .map(itemDto -> {
                    Product product = productRepository.findById(itemDto.productId())
                            .orElseThrow(() -> new EntityNotFoundException("Product not found " + itemDto.productId()));

                    return OrderItemMapper.toEntity(itemDto, product);
                }).toList();

        order.setItems(items);
        verifyAndReduceStock(order);
        Order savedOrder = orderRepository.save(order);
        customer.getOrders().add(savedOrder);
        customerRepository.save(customer);
        calculateTotalAmount(savedOrder);

        return OrderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByCustomer(Long idCustomer) {
        return orderRepository.findAllByCustomerId(idCustomer).stream()
                .map(OrderMapper::toDto).toList();
    }

    @Override
    public OrderResponseDTO getOrderById(Long id, Customer currentCustomer) {
        Order order = getOrderOrThrow(id);
        Checking.checkCurrentCustomer(currentCustomer, order);
        return  OrderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO cancelOrder(Long id, Customer currentCustomer) {
        Order order = getOrderOrThrow(id);
        Checking.checkCurrentCustomer(currentCustomer, order);

        if (order.getStatus() != Order.Status.NEW) {
            throw new IllegalStateException("Only NEW orders be cancelled");
        }

        order.setStatus(Order.Status.CANCELLED);
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO updateStatusOrder(Long id, Order.Status status) {
        Order order = getOrderOrThrow(id);
        order.setStatus(status);
        orderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    public void calculateTotalAmount(Order order) {
        BigDecimal total = order.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);
    }

    private void verifyAndReduceStock(Order order) {
        order.getItems().forEach(item -> {
            Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new OutOfStockException("Not enough stock for product: " + product.getName());
            }
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        });
    }

    private Order getOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }
}