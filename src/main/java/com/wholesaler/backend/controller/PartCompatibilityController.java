package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.PartCompatibility;
import com.wholesaler.backend.repository.PartCompatibilityRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/compatibilities")
@Tag(name = "Part Compatibilities", description = "Zarządzanie kompatybilnością części")
public class PartCompatibilityController {
    @Autowired
    private PartCompatibilityRepository partCompatibilityRepository;

    // GET /compatibilities/all - get all compatibilities
    @GetMapping("/all")
    @Operation(summary = "Pobierz wszystkie kompatybilności")
    public List<PartCompatibility> getAllDetails() {
        return partCompatibilityRepository.findAll();
    }

    // GET /compatibilities/{compatibilityId} - get compatibility by ID
    @GetMapping("/{compatibilityId}")
    @Operation(summary = "Pobierz kompatybilność po ID")
    public ResponseEntity<PartCompatibility> getDetail(@PathVariable("compatibilityId") Integer compatibilityId) {
        Optional<PartCompatibility> compatibility = partCompatibilityRepository.findById(compatibilityId);
        if (compatibility.isPresent()) {
            return new ResponseEntity<>(compatibility.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new compatibility
    @PostMapping
    @Operation(summary = "Dodaj nową kompatybilność")
    public PartCompatibility addDetail(@RequestBody PartCompatibility compatibility) {
        return partCompatibilityRepository.save(compatibility);
    }

    // PUT - update compatibility by ID
    @PutMapping("/{compatibilityId}")
    @Operation(summary = "Aktualizuj kompatybilność o podanym ID")
    public ResponseEntity<PartCompatibility> updateDetail(@PathVariable("compatibilityId") Integer compatibilityId, @RequestBody PartCompatibility updatedCompatibility) {
        Optional<PartCompatibility> detailOptional = partCompatibilityRepository.findById(compatibilityId);
        if (detailOptional.isPresent()) {
            PartCompatibility compatibility = detailOptional.get();
            compatibility.setPart(updatedCompatibility.getPart());
            compatibility.setCar(updatedCompatibility.getCar());
            return ResponseEntity.ok(partCompatibilityRepository.save(compatibility));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete compatibility by ID
    @DeleteMapping("/{compatibilityId}")
    @Operation(summary = "Usuń kompatybilność o podanym ID")
    public ResponseEntity<PartCompatibility> deleteCompatibility(@PathVariable("compatibilityId") Integer compatibilityId) {
        Optional<PartCompatibility> detailOptional = partCompatibilityRepository.findById(compatibilityId);
        if (detailOptional.isPresent()) {
            partCompatibilityRepository.deleteById(compatibilityId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
