package com.github.Tyrbropro.order_management.controller;

import com.github.Tyrbropro.order_management.dto.product.ProductRequestDTO;
import com.github.Tyrbropro.order_management.dto.product.ProductResponseDTO;
import com.github.Tyrbropro.order_management.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products", description = "Product management APIs")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a new product", description = "Only admins can create new products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO dto){
        return ResponseEntity.status(201).body(productService.createProduct(dto));
    }

    @Operation(summary = "Get all products", description = "Returns a list of all available products")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    @GetMapping("")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Get product by ID", description = "Returns the product with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @Parameter(description = "ID of the product to retrieve", example = "1")
            @PathVariable @Positive Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Update a product", description = "Only admins can update existing products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @Parameter(description = "ID of the product to update", example = "1")
            @PathVariable @Positive Long id,
            @RequestBody @Valid ProductRequestDTO dto){
       return ResponseEntity.ok(productService.updateProduct(id,dto));
    }

    @Operation(summary = "Delete a product", description = "Only admins can delete products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID of the product to delete", example = "1")
            @PathVariable @Positive Long id){
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
    }
}