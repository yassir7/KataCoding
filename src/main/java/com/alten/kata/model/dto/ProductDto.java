package com.alten.kata.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @Size(max = 500)
    private String description;

    private String image;

    @NotBlank
    private String category;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Positive
    private Integer quantity;

    private String internalReference;

    private Long shellId;

    @NotBlank
    private String inventoryStatus;

    private Double rating;
}
