package com.github.Tyrbropro.order.management.service;

import com.github.Tyrbropro.order.management.dto.product.ProductRequestDTO;
import com.github.Tyrbropro.order.management.dto.product.ProductResponseDTO;
import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO dto);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Long id);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto);

    void deleteProduct(Long id);
}
