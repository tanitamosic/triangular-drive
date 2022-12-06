package com.NWT_KTS_project.service.implementations;

import com.NWT_KTS_project.repository.RideRepository;
import com.NWT_KTS_project.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RideServiceImplementation implements RideService {

    @Autowired
    private RideRepository rideRepository;
}
