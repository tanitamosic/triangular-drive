package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.enums.DriverStatus;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private UserRepository userRepository;


    public void setDriverStatus(int driverId, DriverStatus status) {
        Driver driver = (Driver) userRepository.findById(driverId).get();
        driver.setStatus(status);
        userRepository.save(driver);
    }
}
