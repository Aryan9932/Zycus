package com.example.shopstream.controller;

import com.example.shopstream.advisor.CommerceAdvisor;
import com.example.shopstream.advisor.CommerceAdvice;
import com.example.shopstream.advisor.PricingRecommendation;
import com.example.shopstream.advisor.ReorderRecommendation;
import com.example.shopstream.entity.Product;
import com.example.shopstream.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/advisor")
@RequiredArgsConstructor
public class CommerceAdvisorController {

    private final CommerceAdvisor commerceAdvisor;
    private final ProductService productService;

    @GetMapping("/pricing/{id}")
    public ResponseEntity<PricingRecommendation> getPricingRecommendation(@PathVariable Long id) {
        try {
            Product product = productService.getProduct(id);
            PricingRecommendation recommendation = commerceAdvisor.generatePricingRecommendation(product);
            
            if (recommendation != null) {
                return ResponseEntity.ok(recommendation);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reorder/{id}")
    public ResponseEntity<ReorderRecommendation> getReorderRecommendation(@PathVariable Long id) {
        try {
            Product product = productService.getProduct(id);
            ReorderRecommendation recommendation = commerceAdvisor.generateReorderRecommendation(product);
            
            if (recommendation != null) {
                return ResponseEntity.ok(recommendation);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/advice/{id}")
    public ResponseEntity<CommerceAdvice> getCommerceAdvice(@PathVariable Long id) {
        try {
            Product product = productService.getProduct(id);
            CommerceAdvice advice = commerceAdvisor.generateCommerceAdvice(product);
            return ResponseEntity.ok(advice);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/advice/all")
    public ResponseEntity<CommerceAdvice> getOverallCommerceAdvice() {
        // This could aggregate advice across all products
        // For simplicity, returning a general advice
        CommerceAdvice generalAdvice = commerceAdvisor.generateCommerceAdvice(new Product());
        return ResponseEntity.ok(generalAdvice);
    }
}