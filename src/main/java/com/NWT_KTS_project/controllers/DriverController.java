package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.model.Position;

import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.enums.DriverStatus;
import com.NWT_KTS_project.service.DriverService;
import com.NWT_KTS_project.service.PositionService;
import com.NWT_KTS_project.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;


@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private RideService rideService;

    @Autowired
    private DriverService driverService;

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


    @GetMapping("markRideAsFinished/{driverId}/{rideId}")
    public Ride markRideAsFinished(@PathVariable int driverId, @PathVariable int rideId){
        rideService.markRideAsFinished(rideId);
        return rideService.getNextRideForDriver(driverId);
    }

    @GetMapping("/set-status/{id}/{status}")
    public ResponseEntity<String> updateStatus(@PathVariable("id") Integer id, @PathVariable("status") DriverStatus status) {
        this.driverService.setDriverStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get-status/{id}")
    public ResponseEntity<DriverStatus> getStatus(@PathVariable("id") Integer id) {
        DriverStatus ds = this.driverService.getDriverStatus(id);
        return new ResponseEntity<>(ds, HttpStatus.OK);
    }


}
