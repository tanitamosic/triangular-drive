package com.NWT_KTS_project.controllers;


import com.NWT_KTS_project.model.enums.CarType;
import com.NWT_KTS_project.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
public class RouteController {
    @Autowired
    private RouteService service;

    @GetMapping("/price")
    public ResponseEntity<String> getPrice(@RequestParam String distance){
        int dist = Integer.parseInt(distance);
        String res = String.valueOf(service.getPrice(dist));
        return new ResponseEntity<String>(res,HttpStatus.OK);
    }

    @GetMapping("/price/{carType}")
    public ResponseEntity<String> getPriceByType(@RequestParam String distance, @PathVariable  CarType carType){
        int dist = Integer.parseInt(distance);
        String res = String.valueOf(service.getPriceByType(dist,carType));
        return new ResponseEntity<String>(res,HttpStatus.OK);
    }

}
