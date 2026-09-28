package com.example.shopstream.advisor;

import com.example.shopstream.entity.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DeterministicRuleBasedAdvisor implements CommerceAdvisor {
    
    private static final BigDecimal DEFAULT_PRICE_INCREASE = new BigDecimal("0.10"); // 10%
    private static final int DEFAULT_REORDER_MULTIPLIER = 3; // Order 3x the reorder threshold
    @Override
    public PricingRecommendation generatePricingRecommendation(Product product) {
        // Rule 1: If stock is below reorder threshold, recommend 10% price increase
        if (product.getQuantityInStock() != null && product.getReorderThreshold() != null &&
            product.getQuantityInStock() <= product.getReorderThreshold() && 
            product.getQuantityInStock() > 0) {
            
            BigDecimal currentPrice = product.getCurrentPrice();
            if (currentPrice != null && currentPrice.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal recommendedPrice = currentPrice.multiply(BigDecimal.ONE.add(DEFAULT_PRICE_INCREASE));
                return new PricingRecommendation(
                    recommendedPrice,
                    "Stock level (" + product.getQuantityInStock() + ") is at or below reorder threshold (" + 
                    product.getReorderThreshold() + "). Recommend increasing price to reduce demand.",
                    RecommendationPriority.HIGH
                );
            }
        }
        
        // Rule 2: If stock is very low (less than 25% of reorder threshold), recommend higher price increase
        if (product.getQuantityInStock() != null && product.getReorderThreshold() != null &&
            product.getQuantityInStock() > 0 && 
            product.getQuantityInStock() < product.getReorderThreshold() * 0.25) {
            
            BigDecimal currentPrice = product.getCurrentPrice();
            if (currentPrice != null && currentPrice.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal recommendedPrice = currentPrice.multiply(new BigDecimal("1.15")); // 15% increase
                return new PricingRecommendation(
                    recommendedPrice,
                    "Stock level (" + product.getQuantityInStock() + ") is critically low (below 25% of reorder threshold). Recommend significant price increase.",
                    RecommendationPriority.CRITICAL
                );
            }
        }
        
        // Rule 3: If suggested price is significantly different from current price, recommend alignment
        if (product.getCurrentPrice() != null && product.getSuggestedPrice() != null) {
            BigDecimal difference = product.getSuggestedPrice().subtract(product.getCurrentPrice()).abs();
            BigDecimal threshold = product.getCurrentPrice().multiply(new BigDecimal("0.05")); // 5% threshold
            
    @Override
    public ReorderRecommendation generateReorderRecommendation(Product product) {
        // Rule 1: If stock is at or below reorder threshold, recommend reordering
        if (product.getQuantityInStock() != null && product.getReorderThreshold() != null &&
            product.getQuantityInStock() <= product.getReorderThreshold()) {
            
            int recommendedQuantity = product.getReorderThreshold() * DEFAULT_REORDER_MULTIPLIER;
            String reason = "Stock level (" + product.getQuantityInStock() + ") is at or below reorder threshold (" + 
                           product.getReorderThreshold() + "). Recommend ordering " + recommendedQuantity + " units.";
            
            RecommendationPriority priority = RecommendationPriority.HIGH;
            if (product.getQuantityInStock() == 0) {
                priority = RecommendationPriority.CRITICAL;
                reason = "Product is out of stock. Immediate reorder recommended for " + recommendedQuantity + " units.";
            } else if (product.getQuantityInStock() < product.getReorderThreshold() * 0.5) {
                priority = RecommendationPriority.CRITICAL;
                reason = "Stock level (" + product.getQuantityInStock() + ") is critically low. Urgent reorder recommended for " + recommendedQuantity + " units.";
            }
            
            return new ReorderRecommendation(recommendedQuantity, reason, priority);
    @Override
    public CommerceAdvice generateCommerceAdvice(Product product) {
        List<String> recommendations = Arrays.asList();
        String overallStrategy = "Maintain current strategy";
        RecommendationPriority priority = RecommendationPriority.LOW;
        
        // Collect all recommendations
        PricingRecommendation pricingRec = generatePricingRecommendation(product);
        ReorderRecommendation reorderRec = generateReorderRecommendation(product);
        
        if (pricingRec != null || reorderRec != null) {
            overallStrategy = "Active intervention required";
            
            if ((pricingRec != null && pricingRec.getPriority() == RecommendationPriority.CRITICAL) || 
                (reorderRec != null && reorderRec.getPriority() == RecommendationPriority.CRITICAL)) {
                priority = RecommendationPriority.CRITICAL;
                overallStrategy = "Immediate action required";
            } else if ((pricingRec != null && pricingRec.getPriority() == RecommendationPriority.HIGH) || 
                       (reorderRec != null && reorderRec.getPriority() == RecommendationPriority.HIGH)) {
                priority = RecommendationPriority.HIGH;
                overallStrategy = "Priority action recommended";
            } else {
                priority = RecommendationPriority.MEDIUM;
                overallStrategy = "Monitor and consider action";
            }
        }
        
        return new CommerceAdvice(recommendations, overallStrategy, priority);
    }
}
        }
        
        return null; // No recommendation needed
    }
            if (difference.compareTo(threshold) > 0) {
                return new PricingRecommendation(
                    product.getSuggestedPrice(),
                    "Current price deviates significantly from suggested price. Recommend alignment.",
                    RecommendationPriority.MEDIUM
                );
            }
        }
        
        return null; // No recommendation needed
    }