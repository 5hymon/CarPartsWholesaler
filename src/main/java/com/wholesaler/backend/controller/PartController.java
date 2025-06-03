package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Part;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.dto.PartDTO;
import com.wholesaler.backend.service.PartService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parts")
public class PartController {
    private final PartService partService;

    public PartController(PartService partService) {
        this.partService = partService;
    }

    // GET /parts/all - get all parts
    @GetMapping("/all")
    public List<PartDTO> getAllParts() {
        return partService.getAllPartsAsDTO();
    }

    // GET /parts/{partId} - get part by ID
    @GetMapping("/{partId}")
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
    public Part addPart(@RequestBody Part part) {
        return partService.savePart(part);
    }

    // PUT - update part by ID
    @PutMapping("/{partId}")
    public ResponseEntity<Part> updatePart(@PathVariable("partId") Integer partId, @RequestBody Part updatedPart) {
        Optional<Part> partOptional = partService.updatePart(partId, updatedPart);
        if (partOptional.isPresent()) {
            Part part = partOptional.get();
            return ResponseEntity.ok(partService.savePart(part));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete part by ID
    @DeleteMapping("/{partId}")
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
