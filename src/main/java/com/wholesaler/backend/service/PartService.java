package com.wholesaler.backend.service;

import com.wholesaler.backend.dto.CarDTO;
import com.wholesaler.backend.dto.PartDTO;
import com.wholesaler.backend.model.Car;
import com.wholesaler.backend.model.Part;
import com.wholesaler.backend.model.Category;
import com.wholesaler.backend.model.PartCompatibility;
import com.wholesaler.backend.repository.PartRepository;
import com.wholesaler.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartService {
    private final PartRepository partRepository;
    private final CategoryRepository categoryRepository;

    public PartService(PartRepository partRepository, CategoryRepository categoryRepository) {
        this.partRepository = partRepository;
        this.categoryRepository = categoryRepository;
    }

    private PartDTO convertPartToDTO(Part part) {
        List<CarDTO> compatibleCars = part.getCompatibilities().stream()
                .map(this::convertCompatibilityToCarDTO)
                .collect(Collectors.toList());

        return new PartDTO(
                part.getPartId(),
                part.getPartName(),
                part.getUnitPrice(),
                part.getQuantityPerUnit(),
                part.getLeftOnStock(),
                part.getPartDescription(),
                part.getAvailable(),
                part.getCategoryName(),
                compatibleCars
        );
    }

    private CarDTO convertCompatibilityToCarDTO(PartCompatibility compatibility) {
        Car car = compatibility.getCar();
        return new CarDTO(
                car.getCarId(),
                car.getCarMake(),
                car.getCarModel(),
                car.getProductionYears(),
                car.getBodyType(),
                car.getFuelType(),
                car.getEngineType(),
                Collections.emptyList()
        );
    }

    // get all
    public List<PartDTO> getAllPartsAsDTO() {
        List<Part> parts = partRepository.findAll();
        return parts.stream()
                .map(this::convertPartToDTO)
                .collect(Collectors.toList());
    }

    // get by id
    public Optional<PartDTO> getPartByIdAsDTO(Integer id) {
        return partRepository.findById(id).map(this::convertPartToDTO);
    }

    // post new part
    public Part addPart(String partName, Double unitPrice, String quantityPerUnit, Integer leftOnStock, Boolean isAvailable, String partDescription, String categoryName) {
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(categoryName);
        if (optionalCategory.isPresent()) {
            Part part = new Part();
            part.setPartName(partName);
            part.setUnitPrice(unitPrice);
            part.setQuantityPerUnit(quantityPerUnit);
            part.setLeftOnStock(leftOnStock);
            part.setAvailable(isAvailable);
            part.setPartDescription(partDescription);
            part.setCategoryName(categoryName);
            part.setCategory(optionalCategory.get());
            return partRepository.save(part);
        } else {
            return null;
        }
    }

    // post updated part
    public Part savePart(Part part) {
        return partRepository.save(part);
    }

    // put
    public Optional<Part> updatePart(Integer id, String partName, Double unitPrice, String quantityPerUnit, Integer leftOnStock, Boolean isAvailable, String partDescription, String categoryName) {
        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(categoryName);
        if (optionalCategory.isPresent()) {
            return partRepository.findById(id).map(part -> {
                part.setPartName(partName);
                part.setUnitPrice(unitPrice);
                part.setQuantityPerUnit(quantityPerUnit);
                part.setLeftOnStock(leftOnStock);
                part.setAvailable(isAvailable);
                part.setPartDescription(partDescription);
                part.setCategoryName(categoryName);
                part.setCategory(optionalCategory.get());
                return partRepository.save(part);
            });
        } else {
            return Optional.empty();
        }
    }

    // delete
    public void deletePartById(Integer id) {
        if (partRepository.existsById(id)) {
            partRepository.deleteById(id);
        }
    }
}