package com.example.shopstream.advisor;

import com.example.shopstream.entity.Product;

public interface CommerceAdvisor {
    
    /**
     * Generate pricing recommendation for a product
     * @param product the product to analyze
     * @return recommended action or null if no action is needed
     */
    PricingRecommendation generatePricingRecommendation(Product product);
    
    /**
     * Generate reorder recommendation for a product
     * @param product the product to analyze
     * @return recommended action or null if no action is needed
     */
    ReorderRecommendation generateReorderRecommendation(Product product);
    
    /**
     * Generate general commerce advice for a product
     * @param product the product to analyze
     * @return recommended action or null if no action is needed
     */
    CommerceAdvice generateCommerceAdvice(Product product);
}