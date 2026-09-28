package com.example.shopstream.advisor;

public class ReorderRecommendation {
    private Integer recommendedOrderQuantity;
    private String reason;
    private RecommendationPriority priority;
    
    public ReorderRecommendation(Integer recommendedOrderQuantity, String reason, RecommendationPriority priority) {
        this.recommendedOrderQuantity = recommendedOrderQuantity;
        this.reason = reason;
        this.priority = priority;
    }
    
    // Getters and setters
    public Integer getRecommendedOrderQuantity() {
        return recommendedOrderQuantity;
    }
    
    public void setRecommendedOrderQuantity(Integer recommendedOrderQuantity) {
        this.recommendedOrderQuantity = recommendedOrderQuantity;
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