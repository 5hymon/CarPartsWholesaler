package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Part;
import com.wholesaler.backend.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parts")
public class PartController {
    @Autowired
    private PartRepository partRepository;

    // GET /parts/all - get all parts
    @GetMapping("/all")
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    // GET /parts/{partId} - get part by ID
    @GetMapping("/{partId}")
    public ResponseEntity<Part> getPart(@PathVariable("partId") Integer partId) {
        Optional<Part> part = partRepository.findById(partId);
        if (part.isPresent()) {
            return new ResponseEntity<>(part.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new part
    @PostMapping
    public Part addPart(@RequestBody Part part) {
        return partRepository.save(part);
    }

    // PUT - update part by ID
    @PutMapping("/{partId}")
    public ResponseEntity<Part> updatePart(@PathVariable("partId") Integer partId, @RequestBody Part updatedOrder) {
        Optional<Part> partOptional = partRepository.findById(partId);
        if (partOptional.isPresent()) {
            Part part = partOptional.get();
            part.setPartName(updatedOrder.getPartName());
            part.setUnitPrice(updatedOrder.getUnitPrice());
            part.setQuantityPerUnit(updatedOrder.getQuantityPerUnit());
            part.setLeftOnStock(updatedOrder.getLeftOnStock());
            part.setAvailable(updatedOrder.getAvailable());
            part.setPartDescription(updatedOrder.getPartDescription());
            part.setCategory(updatedOrder.getCategory());
            return ResponseEntity.ok(partRepository.save(part));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete part by ID
    @DeleteMapping("/{partId}")
    public ResponseEntity<Part> deleteOrder(@PathVariable("partId") Integer partId) {
        Optional<Part> partOptional = partRepository.findById(partId);
        if (partOptional.isPresent()) {
            partRepository.deleteById(partId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
