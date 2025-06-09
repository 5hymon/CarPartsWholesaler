package com.wholesaler.backend.service;

import com.wholesaler.backend.dto.CarDTO;
import com.wholesaler.backend.dto.PartDTO;
import com.wholesaler.backend.model.Car;
import com.wholesaler.backend.model.Part;
import com.wholesaler.backend.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    private CarDTO convertCarToDTO(Car car) {
        CarDTO dto = new CarDTO();
        dto.setCarId(car.getCarId());
        dto.setCarMake(car.getCarMake());
        dto.setCarModel(car.getCarModel());
        dto.setProductionYears(car.getProductionYears());
        dto.setBodyType(car.getBodyType());
        dto.setFuelType(car.getFuelType());
        dto.setEngineType(car.getEngineType());

        List<PartDTO> parts = car.getCompatibilities().stream()
                .map(pc -> {
                    Part part = pc.getPart();
                    PartDTO partDTO = new PartDTO();
                    partDTO.setPartId(part.getPartId());
                    partDTO.setPartName(part.getPartName());
                    partDTO.setUnitPrice(part.getUnitPrice());
                    partDTO.setQuantityPerUnit(part.getQuantityPerUnit());
                    partDTO.setLeftOnStock(part.getLeftOnStock());
                    partDTO.setAvailable(part.getAvailable());
                    partDTO.setPartDescription(part.getPartDescription());
                    partDTO.setCategoryName(part.getCategoryName());
                    return partDTO;
                }).collect(Collectors.toList());

        dto.setCompatibleParts(parts);

        return dto;
    }

    // get all
    public List<CarDTO> getAllCarsAsDTO() {
        List<Car> cars = carRepository.findByOrderByCarMakeAscCarModelAsc();
        return cars.stream()
                .map(this::convertCarToDTO)
                .collect(Collectors.toList());
    }

    // get by id
    public Optional<CarDTO> getCarByIdAsDTO(Integer id) {
        return carRepository.findById(id).map(this::convertCarToDTO);
    }

    // post new car
    public Car addCar(String carMake, String carModel, String productionYears, String bodyType, String fuelType, String engineType) {
        Car car = new Car();
        car.setCarMake(carMake);
        car.setCarModel(carModel);
        car.setProductionYears(productionYears);
        car.setBodyType(bodyType);
        car.setFuelType(fuelType);
        car.setEngineType(engineType);
        return carRepository.save(car);
    }

    // post updated car
    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    // put
    public Optional<Car> updateCar(Integer id, String carMake, String carModel, String productionYears, String bodyType, String fuelType, String engineType) {
        return carRepository.findById(id).map(car -> {
            car.setCarMake(carMake);
            car.setCarModel(carModel);
            car.setProductionYears(productionYears);
            car.setBodyType(bodyType);
            car.setFuelType(fuelType);
            car.setEngineType(engineType);
            return carRepository.save(car);
        });
    }

    // delete
    public void deleteCar(Integer id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        }
    }
}

