package com.github.Tyrbropro.order.management.service;

import com.github.Tyrbropro.order.management.dto.product.ProductRequestDTO;
import com.github.Tyrbropro.order.management.dto.product.ProductResponseDTO;
import com.github.Tyrbropro.order.management.entity.Product;
import com.github.Tyrbropro.order.management.mapper.ProductMapper;
import com.github.Tyrbropro.order.management.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        return ProductMapper.toDto(productRepository.save(ProductMapper.toEntity(dto)));
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDto).toList();
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return ProductMapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        ProductMapper.updateEntity(product, dto);
        return ProductMapper.toDto(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.deleteById(id);
    }
}