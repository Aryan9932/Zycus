package com.example.shopstream.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ReorderSuggestionDto {
    
    private Long id;
    
    @NotNull(message = "Product ID is required")
    private Long productId;
    
    @Min(value = 1, message = "Suggested quantity must be at least 1")
    private Integer suggestedQuantity;
    
    @Size(max = 500, message = "Reason must be less than 500 characters")
    private String reason;
    
    private String status;
}