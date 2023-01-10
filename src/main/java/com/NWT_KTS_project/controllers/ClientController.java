package com.NWT_KTS_project.controllers;



import com.NWT_KTS_project.model.users.Driver;
import com.NWT_KTS_project.service.DriverService;
import com.NWT_KTS_project.service.ReviewService;
import com.NWT_KTS_project.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/client")
public class ClientController {


    @Autowired
    private ReviewService reviewService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private RideService rideService;

    @PostMapping("reviewRide/{rideId}/{userId}")
    void reviewRide(@PathVariable Integer rideId, @PathVariable Integer userId, @RequestBody String comment, @RequestBody Integer carRating, @RequestBody Integer driverRating){
        reviewService.sendRideReview(rideId, userId, carRating, driverRating, comment);
    }


    @GetMapping("requestRide/{userId}/{longitude}/{latitude}")
    int requestRide(@PathVariable Integer userId, @PathVariable Double longitude, @PathVariable Double latitude,@RequestBody String passengers,@RequestBody boolean petFriendly, @RequestBody boolean babyFriendly){
        int numPassengers = passengers.split(";").length;
        Driver driver = driverService.getAvailableDriver(latitude, longitude, petFriendly, babyFriendly, numPassengers);
        if(driver == null) return -1;

    }



}
