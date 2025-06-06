package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.dto.CarDTO;
import com.wholesaler.backend.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
@Tag(name = "Cars", description = "Zarządzanie samochodami i ich częściami")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // GET /cars/all - get all cars
    @GetMapping("/all")
    @Operation(summary = "Pobierz wszystkie samochody")
    public List<CarDTO> getAllCars() {
        return carService.getAllCarsAsDTO();
    }

    // GET /cars/{carId} - get car by ID
    @GetMapping("/{carId}")
    @Operation(summary = "Pobierz samochód po ID")
    public ResponseEntity<CarDTO> getCar(@PathVariable("carId") Integer carId) {
        Optional<CarDTO> car = carService.getCarByIdAsDTO(carId);
        if (car.isPresent()) {
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new car
    @PostMapping
    @Operation(summary = "Dodaj nowy samochód")
    public Car addCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    // PUT - update car by ID
    @PutMapping(value = "/{carId}")
    @Operation(summary = "Aktualizuj samochód o podanym ID")
    public ResponseEntity<Car> updateCar(
            @PathVariable Integer carId,
            @RequestParam("carMake") String carMake,
            @RequestParam("carModel") String carModel,
            @RequestParam("productionYears") String productionYears,
            @RequestParam("bodyType") String bodyType,
            @RequestParam("fuelType") String fuelType,
            @RequestParam("engineType") String engineType
    ) {
        Optional<Car> carOptional = carService.updateCar(carId, carMake, carModel, productionYears, bodyType, fuelType, engineType);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            return ResponseEntity.ok(carService.saveCar(car));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE — delete car by ID
    @DeleteMapping("/{carId}")
    @Operation(summary = "Usuń samochód o podanym ID")
    public ResponseEntity<Void> deleteCar(@PathVariable("carId") Integer carId) {
        Optional<CarDTO> carOptional = carService.getCarByIdAsDTO(carId);
        if (carOptional.isPresent()) {
            carService.deleteCar(carId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
