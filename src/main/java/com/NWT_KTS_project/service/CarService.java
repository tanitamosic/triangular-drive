package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.Car;
import com.NWT_KTS_project.model.enums.CarMake;
import com.NWT_KTS_project.model.enums.CarType;
import com.NWT_KTS_project.model.enums.Color;
import com.NWT_KTS_project.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<CarType> getAllCarTypes() {
        return List.of(CarType.values());
    }

    public List<CarMake> getAllCarMakers() {
        return List.of(CarMake.values());
    }

    public List<Color> getAllCarColors() {
        return List.of(Color.values());
    }

    public List<Car> getAllCars() { return carRepository.findAll(); }
}
