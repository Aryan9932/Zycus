package com.example.shopstream.repository;

import com.example.shopstream.entity.PricingSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PricingSuggestionRepository extends JpaRepository<PricingSuggestion, Long> {
    List<PricingSuggestion> findByProductId(Long productId);
    List<PricingSuggestion> findByStatus(com.example.shopstream.entity.SuggestionStatus status);
}
