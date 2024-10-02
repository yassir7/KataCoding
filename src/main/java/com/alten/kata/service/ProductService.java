package com.alten.kata.service;

import com.alten.kata.model.Product;
import com.alten.kata.model.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface ProductService {
    Page<ProductDto> getAllProducts(Pageable pageable);
    Optional<ProductDto> getProductById(Long id);
    ProductDto createProduct(ProductDto productDto);
    ProductDto patchProduct(Long id, Map<String, Object> updates);  // New method for PATCH
    void deleteProduct(Long id);
}