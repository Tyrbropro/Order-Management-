package com.github.Tyrbropro.order.management.service;

import com.github.Tyrbropro.order.management.dto.customer.CustomerRequestDTO;
import com.github.Tyrbropro.order.management.dto.customer.CustomerResponseDTO;
import com.github.Tyrbropro.order.management.entity.Customer;
import com.github.Tyrbropro.order.management.repository.CustomerRepository;
import com.github.Tyrbropro.order.management.util.TestDataFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    private CustomerRepository customerRepository;
    private CustomerRequestDTO customerRequestDTO;
    private CustomerServiceImpl customerServiceImpl;
    private Customer customer;

    private final long id = 1L;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerRequestDTO = TestDataFactory.createCustomerRequestDTO();
        customerServiceImpl = new CustomerServiceImpl(customerRepository);
        customer = TestDataFactory.createCustomer(id);
    }

    @Test
    void createCustomer_success() {
        when(customerRepository.save(any())).thenReturn(customer);

        CustomerResponseDTO customerResponseDTO = customerServiceImpl.createCustomer(customerRequestDTO);

        assertNotNull(customerResponseDTO);
        assertEquals(Customer.Role.CUSTOMER, customerResponseDTO.role());
        assertEquals("TestEmail@example.com", customerResponseDTO.email());
    }

    @Test
    void getCustomerById_success() {
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        CustomerResponseDTO customerResponseDTO = customerServiceImpl.getCustomerById(id);

        assertNotNull(customerResponseDTO);
        assertEquals(Customer.Role.CUSTOMER, customerResponseDTO.role());
    }

    @Test
    void getCustomerById_EntityNotFound() {
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerServiceImpl.getCustomerById(id));
    }

    @Test
    void updateCustomer_success() {
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        CustomerResponseDTO customerResponseDTO = customerServiceImpl.updateCustomer(id, customerRequestDTO);

        assertNotNull(customerResponseDTO);
        assertEquals(Customer.Role.CUSTOMER, customerResponseDTO.role());
    }

    @Test
    void updateCustomer_EntityNotFound() {
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerServiceImpl.updateCustomer(id, customerRequestDTO));
    }

    @Test
    void deleteCustomer_success() {
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        customerServiceImpl.deleteCustomer(id);
    }

    @Test
    void deleteCustomer_EntityNotFound() {
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerServiceImpl.deleteCustomer(id));
    }
}
