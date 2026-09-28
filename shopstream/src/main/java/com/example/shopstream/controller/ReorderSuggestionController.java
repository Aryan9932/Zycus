package com.example.shopstream.controller;

import com.example.shopstream.dto.ReorderSuggestionDto;
import com.example.shopstream.entity.ReorderSuggestion;
import com.example.shopstream.service.ReorderSuggestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reorder-suggestions")
public class ReorderSuggestionController {
    
    @Autowired
private ReorderSuggestionService reorderSuggestionService;
    
    @GetMapping
    public List<ReorderSuggestionDto> getAllReorderSuggestions() {
        return reorderSuggestionService.getAllSuggestions().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ReorderSuggestionDto> getReorderSuggestionById(@PathVariable Long id) {
        return reorderSuggestionService.getSuggestionById(id)
                .map(suggestion -> new ResponseEntity<>(convertToDto(suggestion), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<ReorderSuggestionDto> createReorderSuggestion(@Valid @RequestBody ReorderSuggestionDto reorderSuggestionDto) {
        ReorderSuggestion reorderSuggestion = convertToEntity(reorderSuggestionDto);
        ReorderSuggestion savedReorderSuggestion = reorderSuggestionService.saveSuggestion(reorderSuggestion);
        return new ResponseEntity<>(convertToDto(savedReorderSuggestion), HttpStatus.CREATED);
    }
    
    @PatchMapping("/{id}/accept")
    public ResponseEntity<ReorderSuggestionDto> acceptReorderSuggestion(@PathVariable Long id) {
        ReorderSuggestion updatedSuggestion = reorderSuggestionService.acceptSuggestion(id);
        if (updatedSuggestion != null) {
            return new ResponseEntity<>(convertToDto(updatedSuggestion), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PatchMapping("/{id}/reject")
    public ResponseEntity<ReorderSuggestionDto> rejectReorderSuggestion(@PathVariable Long id) {
        ReorderSuggestion updatedSuggestion = reorderSuggestionService.rejectSuggestion(id);
        if (updatedSuggestion != null) {
            return new ResponseEntity<>(convertToDto(updatedSuggestion), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    private ReorderSuggestionDto convertToDto(ReorderSuggestion reorderSuggestion) {
        ReorderSuggestionDto dto = new ReorderSuggestionDto();
        dto.setId(reorderSuggestion.getId());
        dto.setProductId(reorderSuggestion.getProductId());
        dto.setSuggestedQuantity(reorderSuggestion.getSuggestedQuantity());
        dto.setReason(reorderSuggestion.getReason());
        dto.setStatus(reorderSuggestion.getStatus().name());
        return dto;
    }
    
    private ReorderSuggestion convertToEntity(ReorderSuggestionDto dto) {
        ReorderSuggestion reorderSuggestion = new ReorderSuggestion();
        reorderSuggestion.setId(dto.getId());
        reorderSuggestion.setProductId(dto.getProductId());
        reorderSuggestion.setSuggestedQuantity(dto.getSuggestedQuantity());
        reorderSuggestion.setReason(dto.getReason());
        if (dto.getStatus() != null) {
            try {
                reorderSuggestion.setStatus(com.example.shopstream.entity.SuggestionStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException e) {
                // Default to PENDING if invalid status provided
                reorderSuggestion.setStatus(com.example.shopstream.entity.SuggestionStatus.PENDING);
            }
        }
        return reorderSuggestion;
    }
}