package com.github.Tyrbropro.order.management.service;

import com.github.Tyrbropro.order.management.dto.customer.CustomerRequestDTO;
import com.github.Tyrbropro.order.management.dto.customer.CustomerResponseDTO;


public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO dto);

    CustomerResponseDTO getCustomerById(Long id);

    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO details);

    void deleteCustomer(Long id);
}
