package com.github.Tyrbropro.order.management.service;

import com.github.Tyrbropro.order.management.dto.order.AddOrderItemDTO;
import com.github.Tyrbropro.order.management.dto.order.RemoveOrderItemDTO;
import com.github.Tyrbropro.order.management.dto.order.UpdateOrderItemQuantityDTO;
import com.github.Tyrbropro.order.management.entity.Customer;
import com.github.Tyrbropro.order.management.entity.Order;
import com.github.Tyrbropro.order.management.entity.Product;
import com.github.Tyrbropro.order.management.exception.OutOfStockException;
import com.github.Tyrbropro.order.management.repository.CustomerRepository;
import com.github.Tyrbropro.order.management.repository.OrderRepository;
import com.github.Tyrbropro.order.management.repository.ProductRepository;
import com.github.Tyrbropro.order.management.util.TestDataFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderItemServiceTest {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    private OrderItemService orderItemService;

    private Order order;
    private Product product;
    private Customer customer;

    private final long id = 1L;

    private final AddOrderItemDTO addOrderItemDTO = new AddOrderItemDTO(id, 2);
    private final RemoveOrderItemDTO removeOrderItemDTO = new RemoveOrderItemDTO(id);
    private final UpdateOrderItemQuantityDTO updateOrderItemQuantityDTO = new UpdateOrderItemQuantityDTO(id, 5);

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        productRepository = mock(ProductRepository.class);
        CustomerRepository customerRepository = mock(CustomerRepository.class);

        OrderService orderService = new OrderService(orderRepository, customerRepository, productRepository);
        orderItemService = new OrderItemService(orderRepository, productRepository, orderService);

        order = TestDataFactory.createOrder(id, id, id, id);
        product = TestDataFactory.createProduct(id);
        customer = TestDataFactory.createCustomer(id);

        order.setItems(new ArrayList<>());
    }

    @Test
    void addOrderItem_success() {
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        orderItemService.addItemToOrder(id, addOrderItemDTO, customer);

        assertEquals(8, product.getStock());
        assertEquals(1, order.getItems().size());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void addOrderItem_orderNotFound() {
        when(orderRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderItemService.addItemToOrder(id, addOrderItemDTO, customer));
    }

    @Test
    void addOrderItem_productNotFound() {
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderItemService.addItemToOrder(id, addOrderItemDTO, customer));
    }

    @Test
    void addOrderItem_outOfStock() {
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        AddOrderItemDTO dtoOutOfStock = new AddOrderItemDTO(id, 999);
        assertThrows(OutOfStockException.class, () -> orderItemService.addItemToOrder(id, dtoOutOfStock , customer));
    }

    @Test
    void addOrderItem_accessDenied(){
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Customer otherCustomer = TestDataFactory.createCustomer(2L);
        assertThrows(AccessDeniedException.class, () -> orderItemService.addItemToOrder(id, addOrderItemDTO , otherCustomer));
    }

    @Test
    void removeOrderItem_success() {
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        orderItemService.addItemToOrder(id, addOrderItemDTO, customer);
        order.getItems().get(0).setId(id);
        assertEquals(new BigDecimal("100.00"), order.getTotalAmount());

        orderItemService.removeItemFromOrder(id, removeOrderItemDTO, customer);
        assertEquals(new BigDecimal("0"), order.getTotalAmount());

        assertEquals(8, product.getStock());
        assertEquals(0, order.getItems().size());
    }

    @Test
    void removeOrderItem_orderNotFound() {
        when(orderRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderItemService.removeItemFromOrder(id, removeOrderItemDTO , customer));
    }

    @Test
    void removeOrderItem_orderItemNotFound() {
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        assertThrows(EntityNotFoundException.class, () -> orderItemService.removeItemFromOrder(id, removeOrderItemDTO , customer));
    }

    @Test
    void removeOrderItem_accessDenied(){
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        orderItemService.addItemToOrder(id, addOrderItemDTO, customer);
        order.getItems().get(0).setId(id);

        Customer otherCustomer = TestDataFactory.createCustomer(2L);
        assertThrows(AccessDeniedException.class, () -> orderItemService.removeItemFromOrder(id, removeOrderItemDTO , otherCustomer));
    }

    @Test
    void updateItemQuantity_success() {
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        orderItemService.addItemToOrder(id, addOrderItemDTO, customer);
        order.getItems().get(0).setId(id);
        assertEquals(2, order.getItems().get(0).getQuantity());

        orderItemService.updateItemQuantity(id, updateOrderItemQuantityDTO, customer);
        assertEquals(5, order.getItems().get(0).getQuantity());
    }

    @Test
    void updateItemQuantity_orderNotFound() {
        when(orderRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> orderItemService.updateItemQuantity(id, updateOrderItemQuantityDTO, customer));
    }

    @Test
    void updateItemQuantity_itemOrderNotFound() {
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        orderItemService.addItemToOrder(id, addOrderItemDTO, customer);
        order.getItems().get(0).setId(999L);

        assertThrows(EntityNotFoundException.class, () -> orderItemService.updateItemQuantity(id, updateOrderItemQuantityDTO, customer));
    }

    @Test
    void updateItemQuantity_outOfStock() {
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        orderItemService.addItemToOrder(id, addOrderItemDTO, customer);
        order.getItems().get(0).setId(id);

        UpdateOrderItemQuantityDTO outOfStockDTO = new UpdateOrderItemQuantityDTO(id, 999);
        assertThrows(OutOfStockException.class, () -> orderItemService.updateItemQuantity(id, outOfStockDTO, customer));
    }

    @Test
    void updateItemQuantity_accessDenied() {
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        orderItemService.addItemToOrder(id, addOrderItemDTO, customer);
        order.getItems().get(0).setId(id);

        Customer otherCustomer = TestDataFactory.createCustomer(2L);
        assertThrows(AccessDeniedException.class, () -> orderItemService.updateItemQuantity(id, updateOrderItemQuantityDTO, otherCustomer));
    }
}
