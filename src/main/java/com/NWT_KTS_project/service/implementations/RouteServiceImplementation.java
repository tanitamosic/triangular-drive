package com.NWT_KTS_project.service.implementations;

import com.NWT_KTS_project.repository.RouteRepository;
import com.NWT_KTS_project.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImplementation implements RouteService {

    @Autowired
    private RouteRepository routeRepository;
}
