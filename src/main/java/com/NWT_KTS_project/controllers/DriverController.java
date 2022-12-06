package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.model.Position;
import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.service.PositionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;


@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @Autowired
    private PositionService positionService;



    @PostMapping("updatePosition/{id}/{latitude}/{longitude}")
    public void updatePosition(@PathVariable int id, @PathVariable double latitude, @PathVariable double longitude){
        positionService.updatePosition(id, new Position(latitude, longitude));
    }

    @GetMapping("getPositionById/{id}")
    public Position getPositionById(@PathVariable int id){
        return positionService.getPosition(id);
    }

    @GetMapping("getPositions")
    public HashMap<Integer, Position> getPositions(){
        return positionService.getPositions();
    }


}
