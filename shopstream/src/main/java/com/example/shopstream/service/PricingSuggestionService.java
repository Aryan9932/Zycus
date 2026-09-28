package com.example.shopstream.service;

import com.example.shopstream.entity.PricingSuggestion;
import com.example.shopstream.entity.Product;
import com.example.shopstream.entity.SuggestionStatus;
import com.example.shopstream.repository.PricingSuggestionRepository;
import com.example.shopstream.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PricingSuggestionService implements SuggestionService<PricingSuggestion> {
    
    @Autowired
    private PricingSuggestionRepository pricingSuggestionRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public List<PricingSuggestion> getAllSuggestions() {
        return pricingSuggestionRepository.findAll();
    }
    
    @Override
    public Optional<PricingSuggestion> getSuggestionById(Long id) {
        return pricingSuggestionRepository.findById(id);
    }
    
    @Override
    public PricingSuggestion saveSuggestion(PricingSuggestion suggestion) throws DataAccessException {
        return pricingSuggestionRepository.save(suggestion);
    }
    
    @Override
    public void deleteSuggestion(Long id) throws DataAccessException {
        pricingSuggestionRepository.deleteById(id);
    }
    
    @Override
    public List<PricingSuggestion> getSuggestionsByProductId(Long productId) {
        return pricingSuggestionRepository.findByProductId(productId);
    }
    
    @Override
    public List<PricingSuggestion> getSuggestionsByStatus(SuggestionStatus status) {
        return pricingSuggestionRepository.findByStatus(status);
    }
    
    @Override
    @Transactional
    public PricingSuggestion acceptSuggestion(Long id) {
        Optional<PricingSuggestion> optionalSuggestion = pricingSuggestionRepository.findById(id);
        if (optionalSuggestion.isPresent()) {
            PricingSuggestion suggestion = optionalSuggestion.get();
            if (suggestion.getStatus() == SuggestionStatus.PENDING) {
                suggestion.setStatus(SuggestionStatus.ACCEPTED);
                PricingSuggestion updatedSuggestion = pricingSuggestionRepository.save(suggestion);
                
                // Update the product's current price and status
                Optional<Product> optionalProduct = productRepository.findById(suggestion.getProductId());
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setCurrentPrice(suggestion.getSuggestedPrice());
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
    public PricingSuggestion rejectSuggestion(Long id) {
        Optional<PricingSuggestion> optionalSuggestion = pricingSuggestionRepository.findById(id);
        if (optionalSuggestion.isPresent()) {
            PricingSuggestion suggestion = optionalSuggestion.get();
            if (suggestion.getStatus() == SuggestionStatus.PENDING) {
                suggestion.setStatus(SuggestionStatus.REJECTED);
                PricingSuggestion updatedSuggestion = pricingSuggestionRepository.save(suggestion);
                
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