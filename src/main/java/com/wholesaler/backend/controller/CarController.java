package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Car;
import com.wholesaler.backend.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private CarRepository carRepository;

    // GET /cars/all - get all cars
    @GetMapping("/all")
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    // GET /cars/{carId} - get car by ID
    @GetMapping("{carId}")
    public ResponseEntity<Car> getCar(@PathVariable("carId") Integer carId) {
        Optional<Car> car = carRepository.findById(carId);
        if (car.isPresent()) {
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new car
    @PostMapping
    public Car addCar(@RequestBody Car car) {
        return carRepository.save(car);
    }

    // PUT - update car by ID
    @PutMapping("/{carId}")
    public ResponseEntity<Car> updateCar(@PathVariable("carId") Integer carId, @RequestBody Car updatedCar) {
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            Car car = carOptional.get();
            car.setCarMake(updatedCar.getCarMake());
            car.setCarModel(updatedCar.getCarModel());
            car.setProductionYears(updatedCar.getProductionYears());
            car.setBodyType(updatedCar.getBodyType());
            car.setFuelType(updatedCar.getFuelType());
            car.setEngineType(updatedCar.getEngineType());
            return ResponseEntity.ok(carRepository.save(car));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE — delete car by ID
    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable("carId") Integer carId) {
        Optional<Car> carOptional = carRepository.findById(carId);
        if (carOptional.isPresent()) {
            carRepository.deleteById(carId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
