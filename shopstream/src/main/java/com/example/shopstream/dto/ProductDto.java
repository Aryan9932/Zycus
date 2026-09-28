package com.example.shopstream.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDto {
    
    private Long id;
    
    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Product name must be less than 255 characters")
    private String name;
    
    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;
    
    @NotNull(message = "Current price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Current price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Current price must have maximum 10 digits and 2 decimal places")
    private BigDecimal currentPrice;
    
    @NotNull(message = "Suggested price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Suggested price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Suggested price must have maximum 10 digits and 2 decimal places")
    private BigDecimal suggestedPrice;
    
    @Size(max = 100, message = "Category must be less than 100 characters")
    private String category;
    
    @Size(max = 100, message = "Brand must be less than 100 characters")
    private String brand;
    
    @Min(value = 0, message = "Quantity in stock cannot be negative")
    private Integer quantityInStock = 0;
    
    @Min(value = 0, message = "Reorder threshold cannot be negative")
    private Integer reorderThreshold = 10;
    
    @Size(max = 500, message = "Image URL must be less than 500 characters")
    private String imageUrl;
    
    private String status;
}