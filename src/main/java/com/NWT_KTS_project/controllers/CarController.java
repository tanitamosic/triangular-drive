package com.NWT_KTS_project.controllers;


import com.NWT_KTS_project.model.Car;
import com.NWT_KTS_project.model.enums.CarMake;
import com.NWT_KTS_project.model.enums.CarType;
import com.NWT_KTS_project.model.enums.Color;
import com.NWT_KTS_project.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping("/get-all-car-types")
    public ResponseEntity<List<CarType>> getAllCarTypes() {
        List<CarType> carTypes = carService.getAllCarTypes();
        return new ResponseEntity<>(carTypes, HttpStatus.OK);
    }

    @GetMapping("/get-all-car-makers")
    public ResponseEntity<List<CarMake>> getAllCarMakers() {
        List<CarMake> carMakers = carService.getAllCarMakers();
        return new ResponseEntity<>(carMakers, HttpStatus.OK);
    }

    @GetMapping("/get-all-car-colors")
    public ResponseEntity<List<Color>> getAllCarColors() {
        List<Color> carColors = carService.getAllCarColors();
        return new ResponseEntity<>(carColors, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

}
