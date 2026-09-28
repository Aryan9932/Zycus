package com.example.shopstream.repository;

import com.example.shopstream.entity.ReorderSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReorderSuggestionRepository extends JpaRepository<ReorderSuggestion, Long> {
    List<ReorderSuggestion> findByProductId(Long productId);
    List<ReorderSuggestion> findByStatus(com.example.shopstream.entity.SuggestionStatus status);
}
