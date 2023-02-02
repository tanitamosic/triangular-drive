package com.NWT_KTS_project.service;

import com.NWT_KTS_project.model.enums.CarType;
import com.NWT_KTS_project.repository.PriceByCarTypeRepository;
import com.NWT_KTS_project.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private PriceByCarTypeRepository priceRepository;

    public double getPrice(double distance) {
        return distance*120;
    }
    public double getPriceByType(double distance, CarType type){
        return distance * 120 + priceRepository.getPriceByType(type);
    }
}
