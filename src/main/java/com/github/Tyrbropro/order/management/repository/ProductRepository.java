package com.github.Tyrbropro.order.management.repository;

import com.github.Tyrbropro.order.management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByName(String productName);
}