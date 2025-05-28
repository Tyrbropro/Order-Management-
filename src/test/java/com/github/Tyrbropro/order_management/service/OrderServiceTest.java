package com.github.Tyrbropro.order_management.service;

import com.github.Tyrbropro.order_management.dto.order.OrderRequestDTO;
import com.github.Tyrbropro.order_management.dto.order.OrderResponseDTO;
import com.github.Tyrbropro.order_management.entity.Customer;
import com.github.Tyrbropro.order_management.entity.Order;
import com.github.Tyrbropro.order_management.entity.Product;
import com.github.Tyrbropro.order_management.exception.OutOfStockException;
import com.github.Tyrbropro.order_management.mapper.OrderItemMapper;
import com.github.Tyrbropro.order_management.repository.CustomerRepository;
import com.github.Tyrbropro.order_management.repository.OrderRepository;
import com.github.Tyrbropro.order_management.repository.ProductRepository;
import com.github.Tyrbropro.order_management.util.TestDataFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;

    private OrderService orderService;

    private Customer customer;
    private Product product;
    private OrderRequestDTO orderRequestDTOs;
    private Order order;

    private final long id = 1L;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        customerRepository = mock(CustomerRepository.class);
        productRepository = mock(ProductRepository.class);

        orderService = new OrderService(orderRepository, customerRepository, productRepository);

        customer = TestDataFactory.createCustomer(id);
        product = TestDataFactory.createProduct(id);
        orderRequestDTOs = TestDataFactory.createOrderRequestDTO(product);
        order = TestDataFactory.createOrder(id, id, id, id);
    }

    @Test
    void createOrder_success() {
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArguments()[0]);

        OrderResponseDTO response = orderService.createOrder(id, orderRequestDTOs);

        assertNotNull(response);
        assertEquals(1, response.orderItems().size());
        assertEquals(new BigDecimal("150.00"), response.totalAmount());

        verify(productRepository).save(any(Product.class));
        verify(orderRepository).save(any(Order.class));
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void createOrder_customerNotFound() {
        long productId = 982L;

        when(customerRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> orderService.createOrder(1L, orderRequestDTOs));
    }

    @Test
    void createOrder_outOfStock() {
        product.setStock(1);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        assertThrows(OutOfStockException.class,
                () -> orderService.createOrder(1L, orderRequestDTOs));

        verify(orderRepository, never()).save(any());
        verify(productRepository, never()).save(any());
    }

    @Test
    void getAllOrdersByCustomer_success() {
        when(orderRepository.findAllByCustomerId(id)).thenReturn(List.of(order));

        List<OrderResponseDTO> orders = orderService.getAllOrdersByCustomer(id);

        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    void getAllOrdersByCustomer_empty() {
        when(orderRepository.findAllByCustomerId(id)).thenReturn(List.of());

        List<OrderResponseDTO> orders = orderService.getAllOrdersByCustomer(id);

        assertNotNull(orders);
        assertEquals(0, orders.size());

        verify(customerRepository, never()).save(any());
    }

    @Test
    void getOrderById_success() {
        Order order = Order.builder()
                .id(id)
                .customer(customer)
                .items(List.of(OrderItemMapper.toEntity(orderRequestDTOs.orderItems().get(0), product)))
                .totalAmount(new BigDecimal("150.00"))
                .build();

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        orderService.createOrder(id, orderRequestDTOs);

        OrderResponseDTO result = orderService.getOrderById(id, customer);

        assertNotNull(result);
        assertEquals(new BigDecimal("150.00"), result.totalAmount());
    }

    @Test
    void getOrderById_EntityNotFound() {
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        assertThrows(EntityNotFoundException.class,
                () -> orderService.getOrderById(id,customer));

        verify(customerRepository, never()).save(any());
        verify(orderRepository, never()).save(any());
    }

    @Test
    void getOrderById_AccessDenied() {
        long otherCustomerId = 999L;
        Customer otherCustomer = TestDataFactory.createCustomer(otherCustomerId);

        Order order = Order.builder()
                .id(id)
                .customer(customer)
                .items(List.of(OrderItemMapper.toEntity(orderRequestDTOs.orderItems().get(0), product)))
                .totalAmount(new BigDecimal("150.00"))
                .build();

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        orderService.createOrder(id, orderRequestDTOs);

        assertThrows(AccessDeniedException.class, () -> orderService.getOrderById(id, otherCustomer));
    }

    @Test
    void cancelOrder_success() {
        Order order = Order.builder()
                .id(id)
                .customer(customer)
                .items(List.of(OrderItemMapper.toEntity(orderRequestDTOs.orderItems().get(0), product)))
                .totalAmount(new BigDecimal("150.00"))
                .status(Order.Status.NEW)
                .build();

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        assertEquals(Order.Status.NEW, orderService.getOrderById(id, customer).status());
        orderService.cancelOrder(id, customer);
        assertEquals(Order.Status.CANCELLED, orderService.getOrderById(id, customer).status());

        verify(orderRepository).save(any());
    }

    @Test
    void cancelOrder_AccessDenied() {
        long otherCustomerId = 999L;
        Customer otherCustomer = TestDataFactory.createCustomer(otherCustomerId);

        Order order = Order.builder()
                .id(id)
                .customer(customer)
                .items(List.of(OrderItemMapper.toEntity(orderRequestDTOs.orderItems().get(0), product)))
                .totalAmount(new BigDecimal("150.00"))
                .status(Order.Status.NEW)
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        assertThrows(AccessDeniedException.class, () -> orderService.cancelOrder(id, otherCustomer));

        verify(orderRepository, never()).save(any());
    }

    @Test
    void cancelOrder_IllegalState() {
        Order order = Order.builder()
                .id(id)
                .customer(customer)
                .items(List.of(OrderItemMapper.toEntity(orderRequestDTOs.orderItems().get(0), product)))
                .totalAmount(new BigDecimal("150.00"))
                .status(Order.Status.SHIPPED)
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class, () -> orderService.cancelOrder(id, customer));
    }

    @Test
    void updateOrder_success() {
        Order.Status status = Order.Status.SHIPPED;

        Order order = Order.builder()
                .id(id)
                .customer(customer)
                .items(List.of(OrderItemMapper.toEntity(orderRequestDTOs.orderItems().get(0), product)))
                .totalAmount(new BigDecimal("150.00"))
                .status(Order.Status.NEW)
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        assertEquals(Order.Status.NEW, orderService.getOrderById(id, customer).status());
        orderService.updateStatusOrder(id, status);
        assertEquals(status, orderService.getOrderById(id, customer).status());

        verify(orderRepository).save(any());
    }

    @Test
    void updateOrder_EntityNotFound() {
        long otherOrderId = 999L;

        Order.Status status = Order.Status.SHIPPED;

        Order order = Order.builder()
                .id(id)
                .customer(customer)
                .items(List.of(OrderItemMapper.toEntity(orderRequestDTOs.orderItems().get(0), product)))
                .totalAmount(new BigDecimal("150.00"))
                .status(Order.Status.NEW)
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

         assertThrows(EntityNotFoundException.class, () -> orderService.updateStatusOrder(otherOrderId, status));

        verify(orderRepository, never()).save(any());
    }
}