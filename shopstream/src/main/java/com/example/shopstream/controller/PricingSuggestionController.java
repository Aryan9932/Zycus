package com.example.shopstream.controller;

import com.example.shopstream.dto.PricingSuggestionDto;
import com.example.shopstream.entity.PricingSuggestion;
import com.example.shopstream.service.PricingSuggestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pricing-suggestions")
public class PricingSuggestionController {
    
    @Autowired
    private PricingSuggestionService pricingSuggestionService;
    
    @GetMapping
    public List<PricingSuggestionDto> getAllPricingSuggestions() {
        return pricingSuggestionService.getAllSuggestions().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PricingSuggestionDto> getPricingSuggestionById(@PathVariable Long id) {
        return pricingSuggestionService.getSuggestionById(id)
                .map(suggestion -> new ResponseEntity<>(convertToDto(suggestion), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<PricingSuggestionDto> createPricingSuggestion(@Valid @RequestBody PricingSuggestionDto pricingSuggestionDto) {
        PricingSuggestion pricingSuggestion = convertToEntity(pricingSuggestionDto);
        PricingSuggestion savedPricingSuggestion = pricingSuggestionService.saveSuggestion(pricingSuggestion);
        return new ResponseEntity<>(convertToDto(savedPricingSuggestion), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{id}/accept")
    public ResponseEntity<PricingSuggestionDto> acceptPricingSuggestion(@PathVariable Long id) {
        PricingSuggestion updatedSuggestion = pricingSuggestionService.acceptSuggestion(id);
        if (updatedSuggestion != null) {
            return new ResponseEntity<>(convertToDto(updatedSuggestion), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PatchMapping("/{id}/reject")
    public ResponseEntity<PricingSuggestionDto> rejectPricingSuggestion(@PathVariable Long id) {
        PricingSuggestion updatedSuggestion = pricingSuggestionService.rejectSuggestion(id);
        if (updatedSuggestion != null) {
            return new ResponseEntity<>(convertToDto(updatedSuggestion), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    private PricingSuggestionDto convertToDto(PricingSuggestion pricingSuggestion) {
        PricingSuggestionDto dto = new PricingSuggestionDto();
        dto.setId(pricingSuggestion.getId());
        dto.setProductId(pricingSuggestion.getProductId());
        dto.setSuggestedPrice(pricingSuggestion.getSuggestedPrice());
        dto.setReason(pricingSuggestion.getReason());
        dto.setStatus(pricingSuggestion.getStatus().name());
        return dto;
    }
    
    private PricingSuggestion convertToEntity(PricingSuggestionDto dto) {
        PricingSuggestion pricingSuggestion = new PricingSuggestion();
        pricingSuggestion.setId(dto.getId());
        pricingSuggestion.setProductId(dto.getProductId());
        pricingSuggestion.setSuggestedPrice(dto.getSuggestedPrice());
        pricingSuggestion.setReason(dto.getReason());
        if (dto.getStatus() != null) {
            try {
                pricingSuggestion.setStatus(com.example.shopstream.entity.SuggestionStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException e) {
                // Default to PENDING if invalid status provided
                pricingSuggestion.setStatus(com.example.shopstream.entity.SuggestionStatus.PENDING);
            }
        }
        return pricingSuggestion;
    }
}