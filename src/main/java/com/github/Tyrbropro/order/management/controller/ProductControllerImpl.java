package com.github.Tyrbropro.order.management.controller;

import com.github.Tyrbropro.order.management.dto.product.ProductRequestDTO;
import com.github.Tyrbropro.order.management.dto.product.ProductResponseDTO;
import com.github.Tyrbropro.order.management.service.ProductServiceImpl;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductControllerImpl implements ProductController {

    private final ProductServiceImpl productServiceImpl;

    public ProductControllerImpl(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @Override
    public ResponseEntity<ProductResponseDTO> createProduct(ProductRequestDTO dto) {
        return ResponseEntity.status(201).body(productServiceImpl.createProduct(dto));
    }

   @Override
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productServiceImpl.getAllProducts());
    }

    @Override
    public ResponseEntity<ProductResponseDTO> getProductById(Long id) {
        return ResponseEntity.ok(productServiceImpl.getProductById(id));
    }

    @Override
    public ResponseEntity<ProductResponseDTO> updateProduct(Long id, ProductRequestDTO dto) {
       return ResponseEntity.ok(productServiceImpl.updateProduct(id, dto));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        productServiceImpl.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}