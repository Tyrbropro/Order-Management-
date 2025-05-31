package com.github.Tyrbropro.order.management.mapper;

import com.github.Tyrbropro.order.management.dto.product.ProductRequestDTO;
import com.github.Tyrbropro.order.management.dto.product.ProductResponseDTO;
import com.github.Tyrbropro.order.management.entity.Product;

public class ProductMapper {
    public static Product toEntity(ProductRequestDTO dto) {
        return Product.builder()
                .name(dto.name())
                .description(dto.description())
                .stock(dto.stock())
                .price(dto.price())
                .build();
    }

    public static ProductResponseDTO toDto(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .stock(product.getStock())
                .price(product.getPrice())
                .build();
    }

    public static void updateEntity(Product product, ProductRequestDTO dto) {
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setStock(dto.stock());
        product.setPrice(dto.price());
    }
}