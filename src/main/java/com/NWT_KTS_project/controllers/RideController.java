package com.NWT_KTS_project.controllers;

import com.NWT_KTS_project.model.Position;
import com.NWT_KTS_project.model.Ride;
import com.NWT_KTS_project.model.Route;
import com.NWT_KTS_project.model.users.Client;
import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.service.PositionService;
import com.NWT_KTS_project.service.RideService;
import com.NWT_KTS_project.util.comparators.ride.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/ride")
public class RideController {

    @Autowired
    private RideService rideService;
    @Autowired
    private PositionService positionService;

    @GetMapping("getRidesByUserId/{id}")
    List<Ride> getRidesByUserId(@PathVariable int id)
    {
        return getRidesByUserIdSort(id, "date","desc");
    }


    @GetMapping("getRidesByUserId/{id}/{sort}/{order}")
    List<Ride> getRidesByUserIdSort(@PathVariable int id,@PathVariable String sort,@PathVariable String order)
    {
        RideComparator comparator = null;
        switch (sort)
        {
            case "date":
                comparator = new RideDateComparator();
                break;
            case "price":
                comparator = new RidePriceComparator();
                break;
            case "distance":
                comparator = new RideDistanceComparator();
                break;
            case "status":
                comparator = new RideStatusComparator();
                break;
            default:
                comparator = new RideDateComparator();
                break;
        }

        List<Ride> rides =  rideService.getRidesByUserId(id,comparator);
        if(!order.equals("desc"))
        {
            Collections.reverse(rides);
        }

        return rides;
    }

    @GetMapping(value="/{driverId}/assigned-ride")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<List<Ride>> getAssignedRide(@PathVariable("driverId") Integer id) {
        List<Ride> r = rideService.getAssignedRide(id);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @GetMapping(value = "/get/{rideId}")
    public ResponseEntity<Ride> getRide(@PathVariable("rideId") Integer id){
        Ride r = rideService.getRideById(id);
        return new ResponseEntity<>(r,HttpStatus.OK);
    }

    @GetMapping(value="/get-position/{rideId}")
    public ResponseEntity<Position> getRidePosition(@PathVariable("rideId") Integer id){
        Driver driver = rideService.getRideById(id).getDriver();
        Position pos = positionService.getPosition(driver.getId());
        return new ResponseEntity<Position>(pos,HttpStatus.OK);
    }

    @GetMapping(value="/get-all-finished-rides")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Ride>> getAllFinishedRides() {
        return new ResponseEntity<>(rideService.getAllFinishedRides(), HttpStatus.OK);
    }
}
