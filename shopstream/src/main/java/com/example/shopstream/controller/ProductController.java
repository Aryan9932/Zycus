package com.example.shopstream.controller;

import com.example.shopstream.entity.Product;
import com.example.shopstream.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<String> updateProductStock(@PathVariable Long id, @RequestBody StockUpdateRequest stockUpdate) {
        try {
            Product updatedProduct = productService.updateStockLevel(id, stockUpdate.getQuantityInStock());
            if (updatedProduct != null) {
                return new ResponseEntity<>("Stock updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid stock level: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<String> recordSale(@PathVariable Long id, @RequestBody SaleRequest saleRequest) {
        try {
            Product updatedProduct = productService.recordSale(id, saleRequest.getQuantitySold());
            if (updatedProduct != null) {
                return new ResponseEntity<>("Sale recorded successfully. New stock level: " + updatedProduct.getQuantityInStock(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Sale failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Helper classes for request bodies
    public static class StockUpdateRequest {
        private Integer quantityInStock;

        public Integer getQuantityInStock() {
            return quantityInStock;
        }

        public void setQuantityInStock(Integer quantityInStock) {
            this.quantityInStock = quantityInStock;
        }
    }

    public static class SaleRequest {
        private Integer quantitySold;

        public Integer getQuantitySold() {
            return quantitySold;
        }

        public void setQuantitySold(Integer quantitySold) {
            this.quantitySold = quantitySold;
        }
    }
}