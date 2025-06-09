package com.wholesaler.backend.service;

import com.wholesaler.backend.dto.PartCompatibilityDTO;
import com.wholesaler.backend.model.Car;
import com.wholesaler.backend.model.Part;
import com.wholesaler.backend.model.PartCompatibility;
import com.wholesaler.backend.repository.PartCompatibilityRepository;
import com.wholesaler.backend.repository.PartRepository;
import com.wholesaler.backend.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartCompatibilityService {
    private final PartCompatibilityRepository partCompatibilityRepository;
    private final PartRepository partRepository;
    private final CarRepository carRepository;

    public PartCompatibilityService(PartCompatibilityRepository partCompatibilityRepository, PartRepository partRepository, CarRepository carRepository) {
        this.partCompatibilityRepository = partCompatibilityRepository;
        this.partRepository = partRepository;
        this.carRepository = carRepository;
    }

    public PartCompatibilityDTO convertCompatibilityToDTO(PartCompatibility pc) {
        var car = pc.getCar();
        var part = pc.getPart();

        return new PartCompatibilityDTO(
                pc.getCompatibilityId(),
                pc.getCarId(),
                pc.getPartId(),
                car.getCarMake(),
                car.getCarModel(),
                car.getProductionYears(),
                car.getBodyType(),
                car.getFuelType(),
                car.getEngineType(),
                part.getPartName(),
                part.getUnitPrice(),
                part.getAvailable()
        );
    }

    // get all
    public List<PartCompatibilityDTO> getAllCompatibilitiesAsDTO() {
        List<PartCompatibility> parts = partCompatibilityRepository.findAll();
        return parts.stream()
                .map(this::convertCompatibilityToDTO)
                .collect(Collectors.toList());
    }

    // get by id
    public Optional<PartCompatibilityDTO> getCompatibilityByIdAsDTO(Integer id) {
        return partCompatibilityRepository.findById(id).map(this::convertCompatibilityToDTO);
    }

    // post new compatibility
    public PartCompatibility addCompatibility(Integer carId, Integer partId) {
        Optional<Car> carOptional = carRepository.findById(carId);
        Optional<Part> partOptional = partRepository.findById(partId);

        if (carOptional.isPresent() && partOptional.isPresent()) {
            Car car = carOptional.get();
            Part part = partOptional.get();
            PartCompatibility partCompatibility = new PartCompatibility();
            partCompatibility.setCarId(carId);
            partCompatibility.setPartId(partId);
            partCompatibility.setCar(car);
            partCompatibility.setPart(part);
            return partCompatibilityRepository.save(partCompatibility);
        } else {
            return null;
        }
    }

    // post updated compatibility
    public PartCompatibility saveCompatibility(PartCompatibility compatibility) {
        return partCompatibilityRepository.save(compatibility);
    }

    // put
    public Optional<PartCompatibility> updateCompatibility(Integer compatibilityId, Integer carId, Integer partId) {
        Optional<PartCompatibility> compatibilityOptional = partCompatibilityRepository.findById(compatibilityId);
        Optional<Car> carOptional = carRepository.findById(carId);
        Optional<Part> partOptional = partRepository.findById(partId);

        if (compatibilityOptional.isPresent() && carOptional.isPresent() && partOptional.isPresent()) {
            Car car = carOptional.get();
            Part part = partOptional.get();

            return partCompatibilityRepository.findById(compatibilityId).map(compatibility -> {
                compatibility.setCarId(carId);
                compatibility.setPartId(partId);
                compatibility.setCar(car);
                compatibility.setPart(part);
                return partCompatibilityRepository.save(compatibility);
            });
        } else {
            return Optional.empty();
        }
    }

    // delete
    public void deleteCompatibilityById(Integer id) {
        if (partCompatibilityRepository.existsById(id)) {
            partCompatibilityRepository.deleteById(id);
        }
    }
}
