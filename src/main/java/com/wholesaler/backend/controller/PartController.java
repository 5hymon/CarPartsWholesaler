package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Part;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.dto.PartDTO;
import com.wholesaler.backend.service.PartService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parts")
@Tag(name = "Parts", description = "Zarządzanie częściami")
public class PartController {
    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    // GET /parts/all - get all parts
    @GetMapping("/all")
    @Operation(summary = "Pobierz wszystkie części")
    public List<PartDTO> getAllParts() {
        return partService.getAllPartsAsDTO();
    }

    // GET /parts/{partId} - get part by ID
    @GetMapping("/{partId}")
    @Operation(summary = "Pobierz część po ID")
    public ResponseEntity<PartDTO> getPart(@PathVariable("partId") Integer partId) {
        Optional<PartDTO> part = partService.getPartByIdAsDTO(partId);
        if (part.isPresent()) {
            return new ResponseEntity<>(part.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new part
    @PostMapping
    @Operation(summary = "Dodaj nową część")
    public Part addPart(
            @RequestParam("partName") String partName,
            @RequestParam("unitPrice") Double unitPrice,
            @RequestParam("quantityPerUnit") String quantityPerUnit,
            @RequestParam("leftOnStock") Integer leftOnStock,
            @RequestParam("isAvailable") Boolean isAvailable,
            @RequestParam("partDescription") String partDescription,
            @RequestParam("categoryName") String categoryName) {
        return partService.addPart(partName, unitPrice, quantityPerUnit, leftOnStock, isAvailable, partDescription, categoryName);
    }

    // PUT - update part by ID
    @PutMapping("/{partId}")
    @Operation(summary = "Aktualizuj część o podanym ID")
    public ResponseEntity<Part> updatePart(
            @PathVariable("partId") Integer partId,
            @RequestParam("partName") String partName,
            @RequestParam("unitPrice") Double unitPrice,
            @RequestParam("quantityPerUnit") String quantityPerUnit,
            @RequestParam("leftOnStock") Integer leftOnStock,
            @RequestParam("isAvailable") Boolean isAvailable,
            @RequestParam("partDescription") String partDescription,
            @RequestParam("categoryName") String categoryName) {
        Optional<Part> partOptional = partService.updatePart(partId, partName, unitPrice, quantityPerUnit, leftOnStock, isAvailable, partDescription, categoryName);
        if (partOptional.isPresent()) {
            Part part = partOptional.get();
            return ResponseEntity.ok(partService.savePart(part));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete part by ID
    @DeleteMapping("/{partId}")
    @Operation(summary = "Usuń część o podanym ID")
    public ResponseEntity<Part> deleteOrder(@PathVariable("partId") Integer partId) {
        Optional<PartDTO> partOptional = partService.getPartByIdAsDTO(partId);
        if (partOptional.isPresent()) {
            partService.deletePartById(partId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
