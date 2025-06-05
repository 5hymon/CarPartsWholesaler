package com.wholesaler.backend.service;

import com.wholesaler.backend.dto.CarDTO;
import com.wholesaler.backend.dto.PartDTO;
import com.wholesaler.backend.model.Car;
import com.wholesaler.backend.model.Part;
import com.wholesaler.backend.model.PartCompatibility;
import com.wholesaler.backend.repository.PartRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartService {
    private final PartRepository partRepository;

    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
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

    // post
    public Part savePart(Part part) {
        return partRepository.save(part);
    }

    // put
    public Optional<Part> updatePart(Integer id, Part updatedData) {
        return partRepository.findById(id).map(part -> {
            part.setPartName(updatedData.getPartName());
            part.setUnitPrice(updatedData.getUnitPrice());
            part.setQuantityPerUnit(updatedData.getQuantityPerUnit());
            part.setLeftOnStock(updatedData.getLeftOnStock());
            part.setAvailable(updatedData.getAvailable());
            part.setPartDescription(updatedData.getPartDescription());
            part.setCategory(updatedData.getCategory());
            return partRepository.save(part);
        });
    }

    // delete
    public void deletePartById(Integer id) {
        if (partRepository.existsById(id)) {
            partRepository.deleteById(id);
        }
    }
}