package com.example.shopstream.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PricingSuggestionDto {
    
    private Long id;
    
    @NotNull(message = "Product ID is required")
    private Long productId;
    
    @NotNull(message = "Suggested price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Suggested price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Suggested price must have maximum 10 digits and 2 decimal places")
    private BigDecimal suggestedPrice;
    
    @Size(max = 500, message = "Reason must be less than 500 characters")
    private String reason;
    
    private String status;
}