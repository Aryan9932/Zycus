package com.example.shopstream.service;

import com.example.shopstream.entity.SuggestionStatus;
import java.util.List;
import java.util.Optional;

public interface SuggestionService<T> {
    List<T> getAllSuggestions();
    Optional<T> getSuggestionById(Long id);
    T saveSuggestion(T suggestion);
    void deleteSuggestion(Long id);
    List<T> getSuggestionsByProductId(Long productId);
    List<T> getSuggestionsByStatus(SuggestionStatus status);
    T acceptSuggestion(Long id);
    T rejectSuggestion(Long id);
}