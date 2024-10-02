package com.alten.kata.service.impl;

import com.alten.kata.model.InventoryStatus;
import com.alten.kata.model.Product;
import com.alten.kata.model.dto.ProductDto;
import com.alten.kata.model.dto.ProductMapper;
import com.alten.kata.repository.ProductRepository;
import com.alten.kata.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    private final ProductMapper productMapper;

    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::convertToDTO);
    }

    @Override
    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::convertToDTO);
    }

    @Override
    public ProductDto createProduct(ProductDto productDTO) {
        Product product = productMapper.convertToEntity(productDTO);
        return productMapper.convertToDTO(productRepository.save(product));
    }

    @Override
    public ProductDto patchProduct(Long id, Map<String, Object> updates) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        // Manually check and update each field if it's present in the updates map
        if (updates.containsKey("name")) {
            product.setName((String) updates.get("name"));
        }
        if (updates.containsKey("description")) {
            product.setDescription((String) updates.get("description"));
        }
        if (updates.containsKey("price")) {
            product.setPrice((Double) updates.get("price"));
        }
        if (updates.containsKey("quantity")) {
            product.setQuantity((Integer) updates.get("quantity"));
        }
        if (updates.containsKey("category")) {
            product.setCategory((String) updates.get("category"));
        }
        if (updates.containsKey("image")) {
            product.setImage((String) updates.get("image"));
        }
        if (updates.containsKey("internalReference")) {
            product.setInternalReference((String) updates.get("internalReference"));
        }
        if (updates.containsKey("inventoryStatus")) {
            product.setInventoryStatus(InventoryStatus.valueOf((String) updates.get("inventoryStatus")));
        }
        if (updates.containsKey("rating")) {
            product.setRating((Double) updates.get("rating"));
        }

        Product updatedProduct = productRepository.save(product);
        return productMapper.convertToDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}