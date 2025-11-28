package com.github.Tyrbropro.order.management.service;

import com.github.Tyrbropro.order.management.dto.product.ProductRequestDTO;
import com.github.Tyrbropro.order.management.dto.product.ProductResponseDTO;
import com.github.Tyrbropro.order.management.entity.Product;
import com.github.Tyrbropro.order.management.repository.ProductRepository;
import com.github.Tyrbropro.order.management.util.TestDataFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductRequestDTO productRequestDTOs;
    private ProductServiceImpl productServiceImpl;
    private Product product;

    private final long id = 1L;

    @BeforeEach
    public void setUp() {
        productRepository = mock(ProductRepository.class);
        productRequestDTOs = TestDataFactory.createProductRequestDTO();
        productServiceImpl = new ProductServiceImpl(productRepository);
        product = TestDataFactory.createProduct(id);
    }

    @Test
    void createProduct_success() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO productResponseDTO = productServiceImpl.createProduct(productRequestDTOs);

        assertNotNull(productResponseDTO);
        assertEquals(10, productResponseDTO.stock());
        assertEquals(new BigDecimal("50.00"), productResponseDTO.price());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getAllProducts_success() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDTO> productResponseDTOS = productServiceImpl.getAllProducts();

        assertNotNull(productResponseDTOS);
        assertEquals(1, productResponseDTOS.size());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getAllProducts_emptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<ProductResponseDTO> productResponseDTOS = productServiceImpl.getAllProducts();

        assertNotNull(productResponseDTOS);
        assertEquals(0, productResponseDTOS.size());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById_success() {
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        ProductResponseDTO productResponseDTO = productServiceImpl.getProductById(id);

        assertNotNull(productResponseDTO);
        assertEquals(10, productResponseDTO.stock());
        assertEquals(new BigDecimal("50.00"), productResponseDTO.price());

        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void getProductById_EntityNotFound() {
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productServiceImpl.getProductById(id));
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void updateProduct_success() {
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDTO productResponseDTO = productServiceImpl.updateProduct(id, productRequestDTOs);

        assertNotNull(productResponseDTO);
        assertEquals(10, productResponseDTO.stock());
        assertEquals(new BigDecimal("50.00"), productResponseDTO.price());
    }

    @Test
    void updateProduct_EntityNotFound() {
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productServiceImpl.updateProduct(id, productRequestDTOs));
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void deleteProduct_success() {
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        productServiceImpl.deleteProduct(id);
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteProduct_EntityNotFound() {
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productServiceImpl.deleteProduct(id));
        verify(productRepository, times(1)).findById(id);
    }
}
