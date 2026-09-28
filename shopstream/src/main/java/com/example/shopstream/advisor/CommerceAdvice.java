package com.example.shopstream.advisor;

import java.util.List;

public class CommerceAdvice {
    private List<String> recommendations;
    private String overallStrategy;
    private RecommendationPriority priority;
    
    public CommerceAdvice(List<String> recommendations, String overallStrategy, RecommendationPriority priority) {
        this.recommendations = recommendations;
        this.overallStrategy = overallStrategy;
        this.priority = priority;
    }
    
    // Getters and setters
    public List<String> getRecommendations() {
        return recommendations;
    }
    
    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
    
    public String getOverallStrategy() {
        return overallStrategy;
    }
    
    public void setOverallStrategy(String overallStrategy) {
        this.overallStrategy = overallStrategy;
    }
    
    public RecommendationPriority getPriority() {
        return priority;
    }
    
    public void setPriority(RecommendationPriority priority) {
        this.priority = priority;
    }
}