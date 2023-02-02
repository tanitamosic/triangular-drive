package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.DTO.ReportDTO;
import com.NWT_KTS_project.model.Position;

import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.enums.DriverStatus;
import com.NWT_KTS_project.model.enums.RideStatus;
import com.NWT_KTS_project.service.DriverService;
import com.NWT_KTS_project.service.PositionService;
import com.NWT_KTS_project.service.ReportService;
import com.NWT_KTS_project.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/driver")
public class DriverController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private RideService rideService;

    @Autowired
    private DriverService driverService;


    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    @PostMapping("setRideStatus/{rideId}/{status}")
    public void setRideStatus(@PathVariable int rideId, @PathVariable RideStatus status){
        rideService.setRideStatus(rideId, status);
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


    @GetMapping("/get-driver-rides/{driverId}/{date1}/{date2}")
    public ResponseEntity<List<Ride>> getTotalDriverIncome(@PathVariable Integer driverId, @PathVariable String date1, @PathVariable String date2) {
        // TODO: IMPLEMENT
        LocalDate d1 = LocalDate.parse(date1, FORMATTER);
        LocalDateTime dateTime1 = d1.atStartOfDay();
        LocalDate d2 = LocalDate.parse(date2, FORMATTER);
        LocalDateTime dateTime2 = d2.atStartOfDay();
        List<Ride> retval = rideService.getDriverRides(driverId, dateTime1, dateTime2);
        return new ResponseEntity<>(retval, HttpStatus.OK);
    }

    @PostMapping(value = "/reject-ride")
    public void rejectRide(@RequestBody ReportDTO dto){
        Ride ride = rideService.getRideById(dto.rideId);
        reportService.makeReport(ride.getDriver(),ride.getPassengers().get(0),dto.reason);
        ride.setStatus(RideStatus.REJECTED);
        rideService.saveRide(ride);
    }

}
