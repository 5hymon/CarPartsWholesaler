package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.dto.CarDTO;
import com.wholesaler.backend.service.CarService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    // GET /cars/all - get all cars
    @GetMapping("/all")
    public List<CarDTO> getAllCars() {
        return carService.getAllCarsAsDTO();
    }

    // GET /cars/{carId} - get car by ID
    @GetMapping("{carId}")
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
    public Car addCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    // PUT - update car by ID
    @PutMapping("/{carId}")
    public ResponseEntity<Car> updateCar(@PathVariable("carId") Integer carId, @RequestBody Car updatedCar) {
        Optional<Car> carOptional = carService.updateCar(carId, updatedCar);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            return ResponseEntity.ok(carService.saveCar(car));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE — delete car by ID
    @DeleteMapping("/{carId}")
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
