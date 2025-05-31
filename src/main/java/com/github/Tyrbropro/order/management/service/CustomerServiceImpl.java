package com.github.Tyrbropro.order.management.service;

import com.github.Tyrbropro.order.management.dto.customer.CustomerRequestDTO;
import com.github.Tyrbropro.order.management.dto.customer.CustomerResponseDTO;
import com.github.Tyrbropro.order.management.entity.Customer;
import com.github.Tyrbropro.order.management.entity.Order;
import com.github.Tyrbropro.order.management.mapper.CustomerMapper;
import com.github.Tyrbropro.order.management.mapper.OrderMapper;
import com.github.Tyrbropro.order.management.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {
        Customer customer = CustomerMapper.toEntity(dto, List.of());

        if (!(dto.orders() == null)) {
            List<Order> orders = dto.orders().stream()
                    .map(orderDto -> OrderMapper.toEntity(orderDto, customer))
                    .collect(Collectors.toList());

            customer.setOrders(orders);
        }

        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.toDto(savedCustomer);
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        return  CustomerMapper.toDto(customer);
    }

    @Override
    @Transactional
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO details) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        List<Order> orders = List.of();

        if (!(details.orders() == null)) {
            orders = details.orders().stream()
                    .map(order -> OrderMapper.toEntity(order, customer)).toList();
        }

        CustomerMapper.updateEntity(customer, details, orders);
        return CustomerMapper.toDto(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        customerRepository.deleteById(id);
    }
}