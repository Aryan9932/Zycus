package com.example.shopstream.service;

import com.example.shopstream.entity.Product;
import com.example.shopstream.entity.ReorderSuggestion;
import com.example.shopstream.entity.SuggestionStatus;
import com.example.shopstream.repository.ProductRepository;
import com.example.shopstream.repository.ReorderSuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ReorderSuggestionService implements SuggestionService<ReorderSuggestion> {
    
    @Autowired
    private ReorderSuggestionRepository reorderSuggestionRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public List<ReorderSuggestion> getAllSuggestions() {
        return reorderSuggestionRepository.findAll();
    }
    
    @Override
    public Optional<ReorderSuggestion> getSuggestionById(Long id) {
        return reorderSuggestionRepository.findById(id);
    }
    
    @Override
    public ReorderSuggestion saveSuggestion(ReorderSuggestion suggestion) throws DataAccessException {
        return reorderSuggestionRepository.save(suggestion);
    }
    
    @Override
    public void deleteSuggestion(Long id) throws DataAccessException {
        reorderSuggestionRepository.deleteById(id);
    }
    
    @Override
    public List<ReorderSuggestion> getSuggestionsByProductId(Long productId) {
        return reorderSuggestionRepository.findByProductId(productId);
    }
    
    @Override
    public List<ReorderSuggestion> getSuggestionsByStatus(SuggestionStatus status) {
        return reorderSuggestionRepository.findByStatus(status);
    }
    
    @Override
    @Transactional
    public ReorderSuggestion acceptSuggestion(Long id) {
        Optional<ReorderSuggestion> optionalSuggestion = reorderSuggestionRepository.findById(id);
        if (optionalSuggestion.isPresent()) {
            ReorderSuggestion suggestion = optionalSuggestion.get();
            if (suggestion.getStatus() == SuggestionStatus.PENDING) {
                suggestion.setStatus(SuggestionStatus.ACCEPTED);
                ReorderSuggestion updatedSuggestion = reorderSuggestionRepository.save(suggestion);
                
                // Update the product's stock level
                Optional<Product> optionalProduct = productRepository.findById(suggestion.getProductId());
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    int newStockLevel = product.getQuantityInStock() + suggestion.getSuggestedQuantity();
                    product.setQuantityInStock(newStockLevel);
                    product.setStatus(com.example.shopstream.entity.ProductStatus.ACTIVE);
                    productRepository.save(product);
                }
                
                return updatedSuggestion;
            }
        }
        return null;
    }
    
    @Override
    @Transactional
    public ReorderSuggestion rejectSuggestion(Long id) {
        Optional<ReorderSuggestion> optionalSuggestion = reorderSuggestionRepository.findById(id);
        if (optionalSuggestion.isPresent()) {
            ReorderSuggestion suggestion = optionalSuggestion.get();
            if (suggestion.getStatus() == SuggestionStatus.PENDING) {
                suggestion.setStatus(SuggestionStatus.REJECTED);
                ReorderSuggestion updatedSuggestion = reorderSuggestionRepository.save(suggestion);
                
                // Update the product's status back to ACTIVE
                Optional<Product> optionalProduct = productRepository.findById(suggestion.getProductId());
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setStatus(com.example.shopstream.entity.ProductStatus.ACTIVE);
                    productRepository.save(product);
                }
                
                return updatedSuggestion;
            }
        }
        return null;
    }
}