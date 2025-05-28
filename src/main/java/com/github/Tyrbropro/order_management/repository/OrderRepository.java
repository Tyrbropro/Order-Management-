package com.github.Tyrbropro.order_management.repository;

import com.github.Tyrbropro.order_management.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomerId(Long idCustomer);
}