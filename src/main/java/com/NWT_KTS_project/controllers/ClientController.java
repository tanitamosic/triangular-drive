package com.NWT_KTS_project.controllers;



import com.NWT_KTS_project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/client")
public class ClientController {


    @Autowired
    private ReviewService reviewService;

    @PostMapping("reviewRide/{rideId}/{userId}")
    void reviewRide(@PathVariable Integer rideId, @PathVariable Integer userId, @RequestBody String comment, @RequestBody Integer carRating, @RequestBody Integer driverRating){
        reviewService.sendRideReview(rideId, userId, carRating, driverRating, comment);
    }






}
