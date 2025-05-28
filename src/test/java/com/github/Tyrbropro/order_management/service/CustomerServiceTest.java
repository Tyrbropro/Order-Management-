package com.github.Tyrbropro.order_management.service;

import com.github.Tyrbropro.order_management.dto.customer.CustomerRequestDTO;
import com.github.Tyrbropro.order_management.dto.customer.CustomerResponseDTO;
import com.github.Tyrbropro.order_management.entity.Customer;
import com.github.Tyrbropro.order_management.repository.CustomerRepository;
import com.github.Tyrbropro.order_management.util.TestDataFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerRequestDTO customerRequestDTO;
    private CustomerService customerService;
    private Customer customer;

    private final long id = 1L;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerRequestDTO = TestDataFactory.createCustomerRequestDTO();
        customerService = new CustomerService(customerRepository);
        customer = TestDataFactory.createCustomer(id);
    }

    @Test
    void createCustomer_success() {
        when(customerRepository.save(any())).thenReturn(customer);

        CustomerResponseDTO customerResponseDTO = customerService.createCustomer(customerRequestDTO);

        assertNotNull(customerResponseDTO);
        assertEquals(Customer.Role.CUSTOMER, customerResponseDTO.role());
        assertEquals("TestEmail@example.com", customerResponseDTO.email());
    }

    @Test
    void getCustomerById_success() {
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        CustomerResponseDTO customerResponseDTO = customerService.getCustomerById(id);

        assertNotNull(customerResponseDTO);
        assertEquals(Customer.Role.CUSTOMER, customerResponseDTO.role());
    }

    @Test
    void getCustomerById_EntityNotFound() {
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerById(id));
    }

    @Test
    void updateCustomer_success() {
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        CustomerResponseDTO customerResponseDTO = customerService.updateCustomer(id, customerRequestDTO);

        assertNotNull(customerResponseDTO);
        assertEquals(Customer.Role.CUSTOMER, customerResponseDTO.role());
    }

    @Test
    void updateCustomer_EntityNotFound() {
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.updateCustomer(id, customerRequestDTO));
    }

    @Test
    void deleteCustomer_success() {
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        customerService.deleteCustomer(id);
    }

    @Test
    void deleteCustomer_EntityNotFound() {
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.deleteCustomer(id));
    }
}
