package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.PartCompatibility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.service.PartCompatibilityService;
import com.wholesaler.backend.dto.PartCompatibilityDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compatibilities")
@Tag(name = "Part Compatibilities", description = "Zarządzanie kompatybilnością części")
public class PartCompatibilityController {
    private final PartCompatibilityService partCompatibilityService;

    public PartCompatibilityController(PartCompatibilityService partCompatibilityService) {
        this.partCompatibilityService = partCompatibilityService;
    }

    // GET /compatibilities/all - get all compatibilities
    @GetMapping("/all")
    @Operation(summary = "Pobierz wszystkie kompatybilności")
    public List<PartCompatibilityDTO> getAllDetails() {
        return partCompatibilityService.getAllCompatibilitiesAsDTO();
    }

    // GET /compatibilities/{compatibilityId} - get compatibility by ID
    @GetMapping("/{compatibilityId}")
    @Operation(summary = "Pobierz kompatybilność po ID")
    public ResponseEntity<PartCompatibilityDTO> getDetail(@PathVariable("compatibilityId") Integer compatibilityId) {
        Optional<PartCompatibilityDTO> compatibility = partCompatibilityService.getCompatibilityByIdAsDTO(compatibilityId);
        if (compatibility.isPresent()) {
            return new ResponseEntity<>(compatibility.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new compatibility
    @PostMapping
    @Operation(summary = "Dodaj nową kompatybilność")
    public PartCompatibility addDetail(
            @RequestParam("carId") Integer carId,
            @RequestParam("partId") Integer partId) {
        return partCompatibilityService.addCompatibility(carId, partId);
    }

    // PUT - update compatibility by ID
    @PutMapping("/{compatibilityId}")
    @Operation(summary = "Aktualizuj kompatybilność o podanym ID")
    public ResponseEntity<PartCompatibility> updateDetail(
            @PathVariable("compatibilityId") Integer compatibilityId,
            @RequestParam("carId") Integer carId,
            @RequestParam("partId") Integer partId) {
        Optional<PartCompatibility> detailOptional = partCompatibilityService.updateCompatibility(compatibilityId, carId, partId);
        if (detailOptional.isPresent()) {
            PartCompatibility compatibility = detailOptional.get();
            return ResponseEntity.ok(partCompatibilityService.saveCompatibility(compatibility));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete compatibility by ID
    @DeleteMapping("/{compatibilityId}")
    @Operation(summary = "Usuń kompatybilność o podanym ID")
    public ResponseEntity<PartCompatibilityDTO> deleteCompatibility(@PathVariable("compatibilityId") Integer compatibilityId) {
        Optional<PartCompatibilityDTO> detailOptional = partCompatibilityService.getCompatibilityByIdAsDTO(compatibilityId);
        if (detailOptional.isPresent()) {
            partCompatibilityService.deleteCompatibilityById(compatibilityId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
