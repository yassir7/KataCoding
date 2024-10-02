package com.alten.kata.model.dto;

import com.alten.kata.model.InventoryStatus;
import com.alten.kata.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto convertToDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDto(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getImage(),
                product.getCategory(),
                product.getPrice(),
                product.getQuantity(),
                product.getInternalReference(),
                product.getShellId(),
                product.getInventoryStatus().name(),
                product.getRating()
        );
    }

    public Product convertToEntity(ProductDto dto) {
        if (dto == null) {
            return null;
        }
        Product product = new Product();
        product.setId(dto.getId());
        product.setCode(dto.getCode());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setInternalReference(dto.getInternalReference());
        product.setShellId(dto.getShellId());
        product.setInventoryStatus(InventoryStatus.valueOf(dto.getInventoryStatus()));
        product.setRating(dto.getRating());
        return product;
    }
}