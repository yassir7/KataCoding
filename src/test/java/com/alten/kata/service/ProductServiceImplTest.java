package com.alten.kata.service;
import com.alten.kata.model.InventoryStatus;
import com.alten.kata.model.Product;
import com.alten.kata.model.dto.ProductDto;
import com.alten.kata.model.dto.ProductMapper;
import com.alten.kata.repository.ProductRepository;
import com.alten.kata.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Map;
import java.util.Optional;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(100.0);

        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Test Product");
        productDto.setPrice(100.0);
    }

    @Test
    void testGetAllProducts() {
        Page<Product> productPage = new PageImpl<>(Arrays.asList(product));
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(productPage);
        when(productMapper.convertToDTO(product)).thenReturn(productDto);

        Page<ProductDto> result = productService.getAllProducts(PageRequest.of(0, 10));
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Product", result.getContent().get(0).getName());
        verify(productRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.convertToDTO(product)).thenReturn(productDto);

        Optional<ProductDto> result = productService.getProductById(1L);
        assertTrue(result.isPresent());
        assertEquals("Test Product", result.get().getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateProduct() {
        when(productMapper.convertToEntity(productDto)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.convertToDTO(product)).thenReturn(productDto);

        ProductDto createdProduct = productService.createProduct(productDto);
        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testPatchProduct() {

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Product Name");
        existingProduct.setPrice(100.0);
        existingProduct.setQuantity(10);
        existingProduct.setCategory("Electronics");
        existingProduct.setDescription("An old product");
        existingProduct.setImage("old_image_url");
        existingProduct.setInternalReference("INT_REF_001");
        existingProduct.setShellId(1L);
        existingProduct.setInventoryStatus(InventoryStatus.INSTOCK);
        existingProduct.setRating(4.5);

        Map<String, Object> updates = Map.of(
                "name", "Updated Product",
                "price", 150.0
        );

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));  // Simulate fetching existing product
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);  // Simulate saving the updated product

        // Simulate converting the updated entity back to DTO
        ProductDto updatedProductDto = new ProductDto(
                1L,
                "ELEC001",
                "Updated Product",
                "An old product",
                "old_image_url",
                "Electronics",
                150.0,
                10,
                "INT_REF_001",
                1L,
                "INSTOCK",
                4.5
        );

        when(productMapper.convertToDTO(existingProduct)).thenReturn(updatedProductDto);

        ProductDto resultProduct = productService.patchProduct(1L, updates);

        assertNotNull(resultProduct);
        assertEquals("Updated Product", resultProduct.getName());
        assertEquals(150.0, resultProduct.getPrice());

        // Verify that repository interactions were correct
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1L);

        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}
