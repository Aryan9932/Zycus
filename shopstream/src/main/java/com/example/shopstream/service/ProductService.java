package com.example.shopstream.service;

import com.example.shopstream.entity.Product;
import com.example.shopstream.entity.ProductState;
import com.example.shopstream.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Create a product
    public Product createProduct(Product product) {

        if (product.getState() == null) {
            product.setState(ProductState.ACTIVE);
        }

        return productRepository.save(product);
    }

    // Get product by ID
    public Product getProduct(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Product not found with id: " + id
                        )
                );
    }

    // Restock product
    public Product restock(Long id, int quantity) {

        Product product = getProduct(id);

        product.restock(quantity);

        return productRepository.save(product);
    }
}