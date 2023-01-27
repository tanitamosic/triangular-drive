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

    //TODO: add pricing by car type
    public double getPrice(int distance) {
        return distance*0.12;
    }
    public double getPriceByType(int distance, CarType type){
        return distance * 0.12 + priceRepository.getPriceByType(type);
    }
}
