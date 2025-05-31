package com.github.Tyrbropro.order_management.service;

import com.github.Tyrbropro.order_management.dto.product.ProductRequestDTO;
import com.github.Tyrbropro.order_management.dto.product.ProductResponseDTO;
import com.github.Tyrbropro.order_management.entity.Product;
import com.github.Tyrbropro.order_management.mapper.ProductMapper;
import com.github.Tyrbropro.order_management.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        return ProductMapper.toDto(productRepository.save(ProductMapper.toEntity(dto)));
    }

    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDto).toList();
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return ProductMapper.toDto(product);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        ProductMapper.updateEntity(product, dto);
        return ProductMapper.toDto(product);
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.deleteById(id);
    }
}