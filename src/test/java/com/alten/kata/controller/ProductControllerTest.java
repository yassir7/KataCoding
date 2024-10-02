package com.alten.kata.controller;

import com.alten.kata.model.dto.ProductDto;
import com.alten.kata.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        productDto = new ProductDto(
                1L, "ELEC001", "Test Product", "A test product",
                "image_url", "Electronics", 100.0, 10,
                "INT_REF001", 1L, "INSTOCK", 4.5
        );
    }

    @Test
    void testGetAllProducts() throws Exception {
        Page<ProductDto> productPage = new PageImpl<>(Arrays.asList(productDto));
        when(productService.getAllProducts(any(PageRequest.class))).thenReturn(productPage);

        mockMvc.perform(get("/v0/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Test Product"));
    }

    @Test
    void testGetProductById() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.of(productDto));

        mockMvc.perform(get("/v0/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.createProduct(any(ProductDto.class))).thenReturn(productDto);

        String requestBody = "{ \"code\": \"ELEC001\", \"name\": \"Test Product\", \"price\": 100.0, \"quantity\": 10, \"category\": \"Electronics\" }";

        mockMvc.perform(post("/v0/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testPatchProduct() throws Exception {
        Map<String, Object> updates = Map.of(
                "name", "Updated Product",
                "price", 150.0
        );
        when(productService.patchProduct(1L, updates)).thenReturn(productDto);

        String requestBody = "{ \"name\": \"Updated Product\", \"price\": 150.0 }";

        mockMvc.perform(patch("/v0/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/v0/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
