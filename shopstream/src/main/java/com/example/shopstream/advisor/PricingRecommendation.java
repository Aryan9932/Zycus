package com.example.shopstream.advisor;

import java.math.BigDecimal;

public class PricingRecommendation {
    private BigDecimal recommendedPrice;
    private String reason;
    private RecommendationPriority priority;
    
    public PricingRecommendation(BigDecimal recommendedPrice, String reason, RecommendationPriority priority) {
        this.recommendedPrice = recommendedPrice;
        this.reason = reason;
        this.priority = priority;
    }
    
    // Getters and setters
    public BigDecimal getRecommendedPrice() {
        return recommendedPrice;
    }
    
    public void setRecommendedPrice(BigDecimal recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public RecommendationPriority getPriority() {
        return priority;
    }
    
    public void setPriority(RecommendationPriority priority) {
        this.priority = priority;
    }
}