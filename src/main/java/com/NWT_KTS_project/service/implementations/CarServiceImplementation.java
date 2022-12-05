package com.NWT_KTS_project.service.implementations;

import com.NWT_KTS_project.repository.CarRepository;
import com.NWT_KTS_project.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImplementation implements CarService {

    @Autowired
    private CarRepository carRepository;

}
