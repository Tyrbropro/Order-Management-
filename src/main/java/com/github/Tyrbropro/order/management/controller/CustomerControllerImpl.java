package com.github.Tyrbropro.order.management.controller;

import com.github.Tyrbropro.order.management.dto.customer.CustomerRequestDTO;
import com.github.Tyrbropro.order.management.dto.customer.CustomerResponseDTO;
import com.github.Tyrbropro.order.management.service.CustomerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerControllerImpl implements CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    public CustomerControllerImpl(CustomerServiceImpl customerServiceImpl) {
        this.customerServiceImpl = customerServiceImpl;
    }

    @Override
    public ResponseEntity<CustomerResponseDTO> createCustomer(CustomerRequestDTO dto) {
        return ResponseEntity.status(201).body(customerServiceImpl.createCustomer(dto));
    }

    @Override
    public ResponseEntity<CustomerResponseDTO> getCustomerById(Long id) {
        return ResponseEntity.ok(customerServiceImpl.getCustomerById(id));
    }

    @Override
    public ResponseEntity<CustomerResponseDTO> updateCustomer(Long id, CustomerRequestDTO details) {
        return ResponseEntity.ok(customerServiceImpl.updateCustomer(id, details));
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(Long id) {
            customerServiceImpl.deleteCustomer(id);
            return ResponseEntity.noContent().build();
    }
}